package chapter02;

import java.util.*;

public class SecondHomework {
    public static void main(String[] args) {

        /*
        자료구조 요리 레시피 메모장 만들기
        입력값

        저장할 자료구조명을 입력합니다. (List / Set / Map)
        내가 좋아하는 요리 제목을 먼저 입력합니다.
        이어서 내가 좋아하는 요리 레시피를 한 문장씩 입력합니다.
        입력을 마쳤으면 마지막에 “끝” 문자를 입력합니다.

        출력값

        입력이 종료되면 저장한 자료구조 이름과 요리 제목을 괄호로 감싸서 먼저 출력해 줍니다.
        이어서, 입력한 모든 문장 앞에 번호를 붙여서 입력 순서에 맞게 모두 출력해 줍니다.
         */

        Scanner scanner = new Scanner(System.in);

        System.out.print("저장할 자료구조명을 입력합니다. (List / Set / Map 이용하기): ");
        String dataStructure = scanner.nextLine().trim();

        // 사용자가 선택한 자료구조에 따라 적절한 객체를 생성합니다.

        // 변수 선언
        Object dataStructureInstance;
        if (dataStructure.equalsIgnoreCase("List")) {
            dataStructureInstance = new ArrayList<String>();
        } else if (dataStructure.equalsIgnoreCase("Set")) {
            dataStructureInstance = new HashSet<String>();
        } else if (dataStructure.equalsIgnoreCase("Map")) {
            dataStructureInstance = new LinkedHashMap<Integer, String>();
        } else {
            System.out.println("지원하지 않는 자료구조입니다.");
            scanner.close();
            return;
        }

        // 요리 제목을 입력받습니다.
        System.out.print("내가 좋아하는 요리 제목을 입력합니다: ");
        String title = scanner.nextLine().trim(); // trim 이란? 공백을 제거한 입력값을 title 변수에 저장합니다.

        // 사용자가 좋아하는 요리 레시피를 입력받습니다.
        System.out.println("이어서 내가 좋아하는 요리 레시피를 한 문장씩 입력합니다. 입력을 마쳤으면 마지막에 ‘끝’을 입력하세요.");
        int recipeNumber = 1; // 레시피 번호를 1로 초기화합니다.

        // 선택한 자료구조에 따라 데이터를 저장하는 방법이 다릅니다.
        if (dataStructure.equalsIgnoreCase("Map")) {
            // 사용자가 Map을 선택한 경우, LinkedHashMap에 레시피를 저장합니다.
            LinkedHashMap<Integer, String> map = (LinkedHashMap<Integer, String>) dataStructureInstance;
            while (true) {
                // 사용자로부터 레시피 한 줄을 입력받습니다.
                System.out.print("레시피를 입력하세요 (끝 입력으로 종료): ");
                String line = scanner.nextLine().trim();
                if (line.equals("끝")) {
                    // 사용자가 "끝"을 입력하면 입력을 종료합니다.
                    break;
                }
                // 레시피를 Map에 저장합니다. 키는 recipeNumber, 값은 사용자가 입력한 레시피입니다.
                map.put(recipeNumber++, line);
            }
        } else {
            // 사용자가 List나 Set을 선택한 경우, 해당 자료구조에 레시피를 저장합니다.
            Collection<String> collection = (Collection<String>) dataStructureInstance;
            while (true) {
                // 사용자로부터 레시피 한 줄을 입력받습니다.
                System.out.print("레시피를 입력하세요 (끝 입력으로 종료): ");
                String line = scanner.nextLine().trim();
                if (line.equals("끝")) {
                    // 사용자가 "끝"을 입력하면 입력을 종료합니다.
                    break;
                }
                // 레시피를 List나 Set에 추가합니다.
                collection.add(line);
            }
        }

        // 사용자가 입력한 요리 제목과 자료구조 이름을 출력합니다.
        System.out.printf("[ %s 으로 저장된 %s ]%n", dataStructure, title);

        // 사용자가 입력한 레시피를 저장된 자료구조에서 하나씩 꺼내어 출력합니다.
        if (dataStructure.equalsIgnoreCase("Map")) {
            // Map에 저장된 경우, 키(번호)와 값(레시피)를 출력합니다.
            LinkedHashMap<Integer, String> map = (LinkedHashMap<Integer, String>) dataStructureInstance;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                // entry.getKey()로 번호를, entry.getValue()로 레시피를 출력합니다.
                System.out.printf("%d. %s%n", entry.getKey(), entry.getValue());
            }
        } else {
            // List나 Set에 저장된 경우, 저장 순서에 따라 번호를 붙여 출력합니다.
            Collection<String> collection = (Collection<String>) dataStructureInstance;
            int number = 1; // 출력할 번호를 1로 초기화합니다.
            for (String recipe : collection) {
                // recipe 변수에 저장된 값을 순서대로 출력합니다.
                System.out.printf("%d. %s%n", number++, recipe);
            }
        }

        scanner.close();
    }
}
