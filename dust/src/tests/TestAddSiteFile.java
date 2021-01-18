package tests;

import persistence.QueriesManager;

public class TestAddSiteFile {

	public static void main(String[] args) {
		boolean result = QueriesManager.getInstance().addSiteInDirectory("Musée de la Perle Robert Wan", "Historic", 0, -17.551369353753934 , -149.55843288787528, "Sous l'égide de Robert WAN qui a consacré avec passion sa vie aux perles, ce musée est conçu pour faire découvrir à la fois leur beauté naturelle et toutes leurs richesses spirituelles. Art, histoire et technologies, mythes, philosophie et religions, poésie et géo-économie… la perle apparue avec l'homo sapiens condense en elle la matière et l'esprit, reliant l'imaginaire aux éléments naturels et le génie industrieux des hommes aux forces cosmiques. Inauguré le 20 octobre 1998, le musée de la perle, situé au boulevard Pomare, reste un lieu à visiter absolument. Le promeneur trouvera également une collection inégalable de perles montées dans la bijouterie de Tahiti Perles.");
		System.out.println(result);
		QueriesManager.getInstance().closeIndex();
	}
}
