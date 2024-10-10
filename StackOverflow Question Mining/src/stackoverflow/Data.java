package stackoverflow;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

// These data classes do NOT contain all possible fields.
// The complete list of fields can be found at https://api.stackexchange.com/docs
public class Data {
    public static class User {
        private User() {

        }

        private long reputation;
        private long user_id;
        private String display_name;
        private String link;

        public long getReputation() {
            return reputation;
        }

        public long getUserId() {
            return user_id;
        }

        public String getDisplayName() {
            return display_name;
        }

        public String getLink() {
            return link;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(display_name, user.display_name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(display_name);
        }

        @Override
        public String toString() {
            return "User{" +
                    "reputation=" + reputation +
                    ", user_id=" + user_id +
                    ", display_name='" + display_name + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }
    }

    public static class Question {
        private Question() {

        }

        private String[] tags;
        private User owner;
        private long view_count;
        /**
         * Is 0 if no answer is accepted
         */
        private long accepted_answer_id;
        private long score;

        private long favorite_count;
        /**
         * All dates in the API are in unix epoch time, which is the number of seconds since midnight UTC January 1st, 1970. The API does not accept or return fractional times, everything should be rounded to the nearest whole second.
         * See https://api.stackexchange.com/docs/dates
         */
        private long creation_date;
        private long question_id;
        private String link;
        private String title;

        private String body;

        private Answer[] answers;

        public String[] getTags() {
            return tags;
        }

        public User getOwner() {
            return owner;
        }

        public long getViewCount() {
            return view_count;
        }

        /**
         * @return The ID of the accepted answer, or 0 if no answer has been accepted.
         */
        public long getAcceptedAnswerId() {
            return accepted_answer_id;
        }

        public long getScore() {
            return score;
        }

        public long getFavoriteCount() {
            return favorite_count;
        }

        /**
         * All dates in the API are in unix epoch time, which is the number of seconds since midnight UTC January 1st, 1970. The API does not accept or return fractional times, everything should be rounded to the nearest whole second.
         * See https://api.stackexchange.com/docs/dates
         */
        public long getCreationDate() {
            return creation_date;
        }

        public long getQuestionId() {
            return question_id;
        }

        public String getLink() {
            return link;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }

        public Answer[] getAnswers() {
            return answers;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Question question = (Question) o;
            return question_id == question.question_id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(question_id);
        }

        @Override
        public String toString() {
            return "Question{" +
                    "title='" + title + '\'' +
                    ", tags=" + Arrays.toString(tags) +
                    ", owner=" + owner +
                    ", view_count=" + view_count +
                    ", accepted_answer_id=" + accepted_answer_id +
                    ", score=" + score +
                    ", favorite_count=" + favorite_count +
                    ", creation_date=" + creation_date +
                    ", question_id=" + question_id +
                    ", link='" + link + '\'' +
                    ", body='" + body.replace("\r", "").replace("\n", "") + '\'' +
                    ", answers=" + Arrays.toString(answers) +
                    '}';
        }
    }

    public static class Answer {
        private Answer() {

        }

        private long answer_id;

        private String body;

        private long creation_date;

        private boolean is_accepted;

        private User owner;

        private int score;

        public long getAnswerId() {
            return answer_id;
        }

        public String getBody() {
            return body;
        }

        /**
         * All dates in the API are in unix epoch time, which is the number of seconds since midnight UTC January 1st, 1970. The API does not accept or return fractional times, everything should be rounded to the nearest whole second.
         * See https://api.stackexchange.com/docs/dates
         */
        public long getCreationDate() {
            return creation_date;
        }

        public boolean isAccepted() {
            return is_accepted;
        }

        public User getOwner() {
            return owner;
        }

        public int getScore() {
            return score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Answer answer = (Answer) o;
            return answer_id == answer.answer_id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(answer_id);
        }

        @Override
        public String toString() {
            return "Answer{" +
                    "answer_id=" + answer_id +
                    ", body='" + body.replace("\r", "").replace("\n", "") + '\'' +
                    ", creation_date=" + creation_date +
                    ", is_accepted=" + is_accepted +
                    ", owner=" + owner +
                    ", score=" + score +
                    '}';
        }
    }

    private static String STACKOVERFLOW_API_URL = "https://api.stackexchange.com/2.3/";
    private static String TOP_100_KOTLIN_QUESTIONS_CONFIG = "questions?pagesize=100&order=desc&sort=votes&tagged=lambda&site=stackoverflow&filter=!LJbtCzGtLcC-OE-.7b_A25";
    private static String RESOURCE_URL = STACKOVERFLOW_API_URL + TOP_100_KOTLIN_QUESTIONS_CONFIG;

    private Question[] items = new Question[0];

    private Data() {
    }

    /**
     * @return The loaded data, or null if an exception occurred
     */
    public static Data loadTop100FromWeb() {
        // HttpClient would be supported in Java 11, fall back to HttpURLConnection to ensure Java 8 compatibility
        try {
            URL url = new URL(RESOURCE_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // StackExchange pages use compression in HTTP access, default is GZIP
            // See: https://api.stackexchange.com/docs/compression
            con.setRequestProperty("Accept-Encoding", "gzip");

            BufferedReader reader = null;
            if ("gzip".equals(con.getContentEncoding())) {
                // As expected, data has been returned compressed
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));
            } else {
                // StackExchange should _never_ send uncompressed data
                // Still, why not have a fallback? :)
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }

            // Use Gson library to convert JSON into Data.Question and Data.User objects
            Gson gson = new Gson();
            Data data = gson.fromJson(reader, Data.class);
            // Shuffle the response to make this exercise a bit more interesting ;)
            List<Question> itemList = Arrays.asList(data.items);
            Collections.shuffle(itemList);
            itemList.toArray(data.items);
            reader.close();

            return data;
        } catch (MalformedURLException ex) {
            System.err.println("Malformed URL: " + RESOURCE_URL);
            ex.printStackTrace();
        } catch (ProtocolException ex) {
            System.err.println("Protocol exception:");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("IO exception:");
            ex.printStackTrace();
        }

        return new Data();
    }

    public Stream<Question> stream() {
        // DONE Generate new Stream<Question> based on the array 'items'
        return Arrays.stream(items);
    }

    // All following TODOs should be implemented using this.stream().... with a single chain of Stream operations.

    public Optional<Question> getQuestionWithHighestScore() {
        // DONE Return the question with the highest score
        return this.stream().max(Comparator.comparing(Question::getScore));
    }

    public Optional<Question> getQuestionWithShortestOwnerName() {
        // DONE Return the question with the shortest owner name
        return this.stream().min(Comparator.comparing(x -> x.getOwner().display_name.length()));
    }


    public List<Question> getAllQuestionsThatStartWith(String search) {
        // DONE Return the list of questions that start with the parameter 'search' in the title or the body
        return this.stream().filter(x -> x.getTitle().startsWith(search) || x.getBody().startsWith(search)).toList();
    }

    public Optional<Question> getQuestionWithLongestAnswerBodies() {
        // DONE Return the question the longest answer bodies, i.e., the question with the most characters across all answer bodies
        return this.stream().max(Comparator.comparing(x -> Arrays.stream(x.getAnswers()).mapToInt(y -> y.getBody().length()).sum()));
    }

    public Set<String> getDistinctQuestionAnswererNames() {
        // DONE Return the distinct user names that answered questions
        return this.stream().flatMap(x -> Arrays.stream(x.getAnswers()).map(y -> y.getOwner().getDisplayName())).collect(Collectors.toSet());
    }

    public List<Question> getQuestionsSortedDescendingByCreationDate() {
        // DONE Return all questions, sorted descending by creation date
        return this.stream().sorted(Comparator.comparing(Question::getCreationDate).reversed()).toList();
    }

    public double[] getScoreViewCountRatios() {
        // DONE Return the ration (score / view count) for each question, sorted ascending
        return this.stream().mapToDouble(x -> (double) x.score / x.getViewCount()).sorted().toArray();
    }



    public List<Question> getQuestionsWithTag(String... tags) {
        // DONE Return a list of all question that are tagged with at least one tag in 'tags'
        return this.stream().filter(x -> Arrays.stream(x.getTags()).anyMatch(y -> Arrays.asList(tags).contains(y))).toList();
    }

    public OptionalDouble getAverageQuestionBodyLength() {
        // DONE Return the average body length of questions using mapToLong() and average()
        return this.stream().mapToLong(q -> q.getBody().length()).average();
    }

    public double getAverageQuestionScore() {
        // DONE Return the average question score using collect() in conjunction with Collectors.averagingLong()
        return this.stream().collect(Collectors.averagingLong(Question::getScore));
    }

    public Optional<User> getUserWithLowestReputationThatWroteAcceptedAnswer() {
        // DONE Find all owners of accepted answers and return the one with the lowest reputation
        return this.stream().flatMap(x -> Arrays.stream(x.getAnswers())).filter(Answer::isAccepted).map(Answer::getOwner).min(Comparator.comparing(User::getReputation));
    }

    public Map<Character, List<Question>> getQuestionsGroupedByFirstTitleLetter() {
        // DONE Return questions grouped by the first character (upper-cased) in their title
        return this.stream().collect(Collectors.groupingBy(x -> Character.toUpperCase(x.getTitle().charAt(0))));
    }

    public Map<Boolean, List<Question>> getPartitionByAcceptedAnswer() {
        // DONE Return all questions, partitioned by the fact if the question has an answer (true) or not (false)
        return this.stream().collect(Collectors.partitioningBy(x -> x.getAcceptedAnswerId() != 0));
    }

    public List<String> getTopAnswerers(int takeNTop) {
        // DONE Return a list of user names in conjunction with their answer count (i.e., a list of Strings "<user_name> answered <answer_count> times")
        // Sort the users descending based on answer count, and only take the takeNTop users
        return this.stream().flatMap(x -> Arrays.stream(x.getAnswers())).collect(Collectors.groupingBy(y -> y.getOwner().getDisplayName(), Collectors.counting())).entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).map(e -> e.getKey() + "answered "  + e.getValue() + " times").limit(takeNTop).toList();
    }

    @Override
    public String toString() {
        return "Data{" +
                "items=" + Arrays.toString(items) +
                '}';
    }
}
