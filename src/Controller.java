import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {

	public static void main(String[] args) throws Exception {
		/*
		 * crawlStorageFolder is a folder where intermediate crawl data is
		 * stored.
		 */
		String crawlStorageFolder = "/Users/Heet/Downloads/soccerData/tempdocs";
		/*
		 * numberOfCrawlers shows the number of concurrent threads that should
		 * be initiated for crawling.
		 */
		int numberOfCrawlers = 10;

		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(crawlStorageFolder);

		config.setPolitenessDelay(1000);

		config.setMaxDepthOfCrawling(2);

		config.setMaxPagesToFetch(100200);
		
		config.setIncludeBinaryContentInCrawling(false);

		config.setResumableCrawling(true);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		controller.addSeed("https://en.wikibooks.org/wiki/Football_(Soccer)");
		controller.addSeed("https://en.wikibooks.org/wiki/Football_(Soccer)/The_Leagues_and_Teams#FIFA_Divisions");
		controller.addSeed("http://www.footballgroundguide.com/");
		controller.addSeed("http://www.worldsoccer.com/");
		controller.addSeed("http://www.fifa.com/");
		controller.addSeed("http://www.footytube.com/");
		controller.addSeed("http://www.conmebol.com/en");
		controller.addSeed("http://www.the-afc.com/");
		controller.addSeed("http://www.cafonline.com/");
		controller.addSeed("http://www.oceaniafootball.com/ofc/");
		controller.addSeed("http://www.laliga.es/en");
		controller.addSeed("http://www.concacaf.com/");
		controller.addSeed("http://www.espnfc.us/");
		controller.addSeed("http://www.goal.com/en-us/");
		controller.addSeed("http://www.uefa.com/");
		controller.addSeed("http://www.tribalfootball.com/");
		controller.addSeed("http://www.foxsports.com/soccer/news");
		controller.addSeed("http://www.soccernews.com/");
		controller.addSeed("https://sports.yahoo.com/soccer/");
		controller.addSeed("http://bleacherreport.com/world-football");
		controller.addSeed("http://sportslens.com/");
		controller.addSeed("http://www.soccerbase.com/");
		controller.addSeed("http://www.101greatgoals.com/");
		controller.addSeed("http://www.football365.com/");
		controller.addSeed("http://www.footballfilter.com/");
		controller.addSeed("http://worldsoccertalk.com/");
		controller.addSeed("https://www.msn.com/en-us/sports/soccer");
		controller.addSeed("https://www.theguardian.com/football");
		controller.addSeed("http://www.fourfourtwo.com/");
		controller.addSeed("http://edition.cnn.com/sport/football");
		controller.addSeed("http://athenasportsnet.com/soccer/");
		controller.addSeed("http://www.soccer24.com/");
		controller.addSeed("https://www.premierleague.com/");
		controller.addSeed("http://www.offthepost.info/");
		controller.addSeed("http://www.vitalfootball.co.uk/");
		
		
		controller.start(MyCrawler.class, numberOfCrawlers);
	}

}