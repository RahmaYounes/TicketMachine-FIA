package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	 @Test
    // On vérifie que le prix affiché correspond au paramètre passé lors de
    // l'initialisation
    // S1 : le prix affiché correspond à l’initialisation.
    void priceIsCorrectlyInitialized() {
        // Paramètres : valeur attendue, valeur effective, message si erreur
        assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
    }

    @Test
    // S2 : la balance change quand on insère de l’argent
    void insertMoneyChangesBalance() {
        machine.insertMoney(10);
        machine.insertMoney(20);
        // Les montants ont été correctement additionnés
        assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
    }

    @Test 
    // S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
    void pasTicketSiPasAssezArgent(){
        machine.insertMoney(PRICE-1);
        assertFalse(machine.printTicket(), "Pas assez d'argent, la machine ne doit pas imprimer");
    }

    @Test
    // S4 : on imprime le ticket si le montant inséré est suffisant
    void ticketSiAssezArgent(){
        machine.insertMoney(PRICE);
        assertTrue(machine.printTicket(), "Le ticket est imprimé");
    }

    @Test
    // S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
    void balanceDecrementeeSiTicket(){
        machine.insertMoney(PRICE+30);
        machine.printTicket();
        assertEquals(30, machine.getBalance(),"La balance n'a pas été décrémentée correctement");
    }

    @Test
    // S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
    void montantMisAJourQaundTicketImprime(){
        machine.insertMoney(PRICE);
        machine.printTicket();
        assertEquals(PRICE,machine.getTotal(),"Mauvaise mise à jour du montant collecté");
        
    }

    @Test
    // S7 : refund()rend correctement la monnaie
    void refundRendLaMonnaie(){
        machine.insertMoney(PRICE);
        assertEquals(machine.refund(),PRICE,"la machine ne rend paas correctement la monnaie");

    }

    @Test
    // S8 : refund()remet la balance à zéro
    void refundRemetAZero(){
        machine.insertMoney(PRICE);
        machine.refund();
        assertEquals(machine.getBalance(),0 ,"la machine ne remet pas la balance à 0");

    }

    @Test
    // S9 : on ne peut pas insérer un montant négatif
    void insererMontantNegatif(){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
        machine.insertMoney(-50);   
        });
    }

    @Test
    // S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
    void ticketPrixNegatif(){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
        TicketMachine machine1 = new TicketMachine(-50);    
        });
    }
}
