package baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int MAX_NUMBER = 9;
    private static final int MIN_NUMBER = 1;
    private static final int NUM_COUNT = 3;

    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");
        boolean continueGame = true;

        while (continueGame) {
            List<Integer> computer = generateRandomNumbers();
            boolean isCorrect = false;

            while (!isCorrect) {
                System.out.print("숫자를 입력해주세요 : ");
                String input = scanner.nextLine();
                List<Integer> userNumbers = parseInput(input);

                String result = checkNumbers(computer, userNumbers);
                System.out.println(result);
                isCorrect = result.equals("3스트라이크");

                if (isCorrect) {
                    System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                    System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
                    String gameCommand = scanner.nextLine().trim();

                    continueGame = gameCommand.equals("1");
                    if (continueGame) {
                        System.out.println("숫자 야구 게임을 시작합니다.");
                    }
                }
            }
        }
    }

    private static List<Integer> generateRandomNumbers() {
        List<Integer> numbers = new ArrayList<>();
        while (numbers.size() < NUM_COUNT) {
            int randomNumber = Randoms.pickNumberInRange(MIN_NUMBER, MAX_NUMBER);
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        return numbers;
    }

    private static List<Integer> parseInput(String input) {
        if (input == null || input.length() != NUM_COUNT) {
            throw new IllegalArgumentException("입력은 정확히 " + NUM_COUNT + "자리 숫자여야 합니다.");
        }

        List<Integer> numbers = new ArrayList<>();
        for (char ch : input.toCharArray()) {
            if (!Character.isDigit(ch)) {
                throw new IllegalArgumentException("모든 입력은 숫자여야 합니다.");
            }
            int number = Character.getNumericValue(ch);
            if (numbers.contains(number)) {
                throw new IllegalArgumentException("모든 숫자는 고유해야 합니다.");
            }
            numbers.add(number);
        }

        return numbers;
    }

    private static String checkNumbers(List<Integer> computer, List<Integer> user) {
        if (computer.size() != NUM_COUNT || user.size() != NUM_COUNT) {
            throw new IllegalArgumentException(NUM_COUNT + "개의 숫자를 제공해야 합니다.");
        }

        int strike = 0, ball = 0;
        for (int i = 0; i < NUM_COUNT; i++) {
            if (computer.get(i).equals(user.get(i))) {
                strike++;
            } else if (computer.contains(user.get(i))) {
                ball++;
            }
        }

        if (strike == 0 && ball == 0) {
            return "낫싱";
        } else if (strike == 3) {
            return strike + "스트라이크";
        } else if (strike == 0) {
            return ball + "볼";
        }

        return  ball + "볼 " + strike + "스트라이크";
    }
}
