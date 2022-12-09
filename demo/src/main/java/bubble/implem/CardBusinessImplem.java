package bubble.implem;

import bubble.CardBusiness;
import bubble.data.Client;
import bubble.external.MockExternalPrintService;

import java.util.Arrays;

import static bubble.implem.CardBusinessImplem.OPTIONS.*;

public class CardBusinessImplem implements CardBusiness {

    public enum OPTIONS {
        GOLD,
        DIAMOND,
        FAMILY,
        GROUP_SCHOOL,
        GROUP_BUSINESS
    }

    @Override
    public int getDiscount(int age, String city, int totalPayment, Integer numberInGroup, OPTIONS... option) {
        final int[] discount = {0};

        // grosse reduction pour nos plus grands clients
        if (option != null && Arrays.asList(option).contains(GOLD)) {
            if (age < 30) {
                discount[0] = Math.round(totalPayment / 20);
            } else if (age > 30 && age < 45) {
                discount[0] = Math.round(totalPayment / 15);
            } else if (age > 50 && age < 65) {
                discount[0] = Math.round(totalPayment / 11);
            } else if (age > 47 && age < 50) {
                discount[0] = Math.round(totalPayment / 12);
            } else {
                discount[0] = Math.round(totalPayment / 5);
            }
        } else if (option != null && Arrays.asList(option).contains(DIAMOND)) {
            if (age < 30) {
                discount[0] = Math.round(totalPayment / 15);
            } else if (age > 30 && age < 45) {
                discount[0] = Math.round(totalPayment / 10);
            } else if (age > 50 && age < 65) {
                discount[0] = Math.round(totalPayment / 5);
            } else {
                discount[0] = Math.round(totalPayment / 2);
            }
        }

        if ("Grenoble".equals(city)) { // partenariat suite à l'opération des bulles à la bastille
            if (age < 40 && age > 16) {
                return 6;
            } else {
                discount[0] = discount[0] + 3;
            }
        }

        // reduction pour les groupes et les familles
        if (option != null && numberInGroup != null && numberInGroup != 0) {
            Arrays.stream(option).sequential().forEach(
                op -> {
                    if (op.equals(GROUP_SCHOOL)) {
                        discount[0] = discount[0] + 12;
                    } else if (op.equals(GROUP_BUSINESS)) {
                        discount[0] = discount[0] + 1;
                    } else {
                        // ce n'est pas une option de groupe on ne fait rien
                    }
                }
            );
        } else if (option != null && Arrays.asList(option).contains(FAMILY)) {
            // grosse reduction pour les familles
            discount[0] = Math.round(totalPayment / 3);
        }

        if (age > 12 && age < 32) {
            discount[0] = discount[0] + 5;
        }

        // petit client petite reduc,
        if (option == null) {
            discount[0] = discount[0] + 1;
        }
        return discount[0];
    }

    @Override
    public boolean printCard(Client client) {
        // on fait appel à une prestataire pour l'impression
        int price = MockExternalPrintService.print(client.getFirstName(), client.getLastName(), client.getCity(), client.getAge());
        // on vérifie le prix pour éviter les "désagréments" de sur facturation lors de l'évènement "des bulles pour la Planète"
        // on met 2 pour l'age, car nos clients ont entre 16 et 67 ans
        int expectedPrice = client.getFirstName().length() + client.getLastName().length() + client.getCity().length() + 2;
        if (price != expectedPrice) {
            throw new IllegalStateException("Le prix du prestataire est différent du prix attendu pour ce client facture:" + price + " attendu:"+expectedPrice);
        } else {
            return true;
        }

    }


}
