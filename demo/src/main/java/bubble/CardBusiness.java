package bubble;

import bubble.data.Client;
import bubble.implem.CardBusinessImplem;

public interface CardBusiness {

    // reduction effectuée lors de l'achat
    int getDiscount(int age, String city, int totalPayment, Integer numberInGroup, CardBusinessImplem.OPTIONS... option);

    // impression de la carte de fidélité
    boolean printCard(Client client);
}
