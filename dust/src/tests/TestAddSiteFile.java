package tests;

import persistence.QueriesManager;

public class TestAddSiteFile {

	public static void main(String[] args) {
		boolean result = QueriesManager.getInstance().addSiteInDirectory("Mus�e de la Perle Robert Wan", "Historic", 0, -17.551369353753934 , -149.55843288787528, "Sous l'�gide de Robert WAN qui a consacr� avec passion sa vie aux perles, ce mus�e est con�u pour faire d�couvrir � la fois leur beaut� naturelle et toutes leurs richesses spirituelles. Art, histoire et technologies, mythes, philosophie et religions, po�sie et g�o-�conomie� la perle apparue avec l'homo sapiens condense en elle la mati�re et l'esprit, reliant l'imaginaire aux �l�ments naturels et le g�nie industrieux des hommes aux forces cosmiques. Inaugur� le 20 octobre 1998, le mus�e de la perle, situ� au boulevard Pomare, reste un lieu � visiter absolument. Le promeneur trouvera �galement une collection in�galable de perles mont�es dans la bijouterie de Tahiti Perles.");
		System.out.println(result);
		QueriesManager.getInstance().closeIndex();
	}
}
