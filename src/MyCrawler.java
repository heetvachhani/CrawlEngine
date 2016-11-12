import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	private static String path = "/Users/Heet/Downloads/soccerData";

	private static final Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		// boolean acceptUrl;
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.startsWith("http:");
		//
		// if (href.startsWith("http:")) {
		// acceptUrl = true;
		// if (href.endsWith("xml")) {
		// acceptUrl = false;
		// } else if (href.contains("s.teoma.com"))
		// acceptUrl = false;
		// else if (href.contains("store.fifa.com") ||
		// href.contains("oldungvar") || href.contains("ajkids.com"))
		// acceptUrl = false;
		// } else {
		// acceptUrl = false;
		// System.out.println("DEBUG: Filtered url: '" + url + "'");
		// }
		//
		// return acceptUrl;
	}

	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		String domain = page.getWebURL().getDomain();
		String parentUrl = page.getWebURL().getParentUrl();
		String anchor = page.getWebURL().getAnchor();

		int parentDocid = page.getWebURL().getParentDocid();
		try {
			PrintWriter out = new PrintWriter(new FileWriter(path + "/doc" + docid, true));

			out.print("{\"DocID\":" + docid);
			out.print(",\"URL\":\"" + url + "\"");
			out.print(",\"ParentID\":" + parentDocid);
			out.print(",\"ParentURL\":\"" + parentUrl + "\"");
			out.print(",\"Domain\":\"" + domain + "\"");
			out.print(",\"Anchor\":\"" + anchor + "\"");

			if (page.getParseData() instanceof HtmlParseData) {
				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String text = htmlParseData.getText().replaceAll("\\s+", " ");
				String title = htmlParseData.getTitle();
				Set<WebURL> outgoingLinks = htmlParseData.getOutgoingUrls();

				out.print(",\"Title\":\"" + title + "\"");
				out.print(",\"NoOfOutgoingLinks\":" + outgoingLinks.size());
				out.print(",\"Text\":\"" + text + "\"}");

			}
			out.close();

		} catch (Exception e) {
			System.out.println("Invalid path to write!!");
		}

	}
}
