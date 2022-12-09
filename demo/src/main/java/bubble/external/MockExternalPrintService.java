package bubble.external;

public class MockExternalPrintService {

    private static boolean isDebugEnabled = false;

    /**
     * Mock fournit par notre service d'impression pour éviter les soucis
     * suite à l'impression de 1000 mauvaises cartes de réduction à la fête "des bulles et des ballons"
     *
     * @param firstName
     * @param lastName
     * @param city
     * @param age
     * @return
     */
    public static int print(String firstName, String lastName, String city, int age) {
        String cleanFirstName = prepareText(firstName);
        String cleanLastName = prepareText(lastName);
        String cleanCity = prepareText(city);
        if (isDebugEnabled) {
            System.out.println("**********************************************************************");
            System.out.println("* " + cleanFirstName + " " + cleanLastName + "(" + age + ") " + cleanCity);
            System.out.println("**********************************************************************");
        }
        return cleanFirstName.length() + cleanLastName.length() + cleanCity.length() + String.valueOf(age).length();
    }

    private static String prepareText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Le texte est manquant");
        }
        if (text.isBlank()) {
            throw new IllegalArgumentException("Le texte est vide");
        }
        return text.trim().toUpperCase(); // on imprime en MAJUSCULE ICI
    }

}
