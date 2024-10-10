package stackoverflow.main;

import inout.Out;
import stackoverflow.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("===================================================================");
        System.out.println("Analysis of Top 100 questions (based on score) tagged with \"lambda\"");
        System.out.println("===================================================================");

        Data data = Data.loadTop100FromWeb();

        Out.println("\n3 random questions (stream().limit(3)):");
        data.stream().limit(3).forEach(x -> Out.println("  \"%s\"".formatted(x.getTitle())));
        Out.println("\nquestion with highest score:");
        Out.println(data.getQuestionWithHighestScore().map(q -> String.format("  \"%s\" with score %d", q.getTitle(), q.getScore())).orElse("not available"));
        Out.println("\nquestion with shortest owner name:");
        Out.println(data.getQuestionWithShortestOwnerName().map(q -> String.format("  \"%s\" by user \"%s\"", q.getTitle(), q.getOwner().getDisplayName())).orElse("not available"));
        Out.println("\nall questions that start with the word 'Can':");
        data.getAllQuestionsThatStartWith("Can").forEach(x -> Out.println("  \"%s\"".formatted(x.getTitle())));
        Out.println("\nquestion with longest answer bodies:");
        Out.println("  \"%s\"".formatted(data.getQuestionWithLongestAnswerBodies().map(Data.Question::getTitle).orElse("not available")));
        Out.println("\ndistinct users that answered questions:");
        Out.println("  %d".formatted(data.getDistinctQuestionAnswererNames().size()));
        Out.println("\nyoungest question:");
        Out.println("  \"%s\"".formatted(data.getQuestionsSortedDescendingByCreationDate().get(0).getTitle()));
        Out.println("\noldest question:");
        Out.println("  \"%s\"".formatted(data.getQuestionsSortedDescendingByCreationDate().get(99).getTitle()));
        Out.println("\n(score / view count) ratios:");
        Out.println("  %s".formatted(Arrays.toString(data.getScoreViewCountRatios())));
        Out.println("\nquestions with tag java-8 or java-9:");
        data.getQuestionsWithTag("java-8", "java-9").forEach(x -> Out.println("  \"%s\"".formatted(x.getTitle())));
        Out.println("\naverage question body length:");
        Out.println("  %s".formatted(data.getAverageQuestionBodyLength().orElse(-1)));
        Out.println("\naverage question score:");
        Out.println("  %s".formatted(data.getAverageQuestionScore()));
        Out.println("\nuser with lowest reputation that wrote an answer:");
        data.getUserWithLowestReputationThatWroteAcceptedAnswer().ifPresent(u -> Out.println("  %s".formatted(u)));
        Out.println("\ngroup questions by first title letter:");
        data.getQuestionsGroupedByFirstTitleLetter().forEach((c, questions) -> {
            Out.println(String.format("  %c: %d questions", c, questions.size()));
        });
        Out.println("\npartition by accepted answer");
        List<Data.Question> answered = data.getPartitionByAcceptedAnswer().get(true);
        List<Data.Question> nonAnswered = data.getPartitionByAcceptedAnswer().get(false);
        Out.println(String.format("  answered: %d", answered.size()));
        Out.println(String.format("  non-answered: %d", nonAnswered.size()));
        Out.println("\ntop answerers");
        data.getTopAnswerers(5).forEach(answererText -> {
            Out.println(String.format("  %s", answererText));
        });
    }
}
