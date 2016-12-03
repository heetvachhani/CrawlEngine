import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	private static String path = "/Users/Heet/Downloads/soccerData/tempdocs/";
	private static final Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return  !FILTERS.matcher(href).matches() && href.startsWith("http:") && !href.endsWith("xml") && !href.contains("teoma.com");
	}

	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		try {
			File file = new File(path + docid);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(url);

			if (page.getParseData() instanceof HtmlParseData) {
				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String text = htmlParseData.getText().replaceAll("\\s+", " ");
				String title = htmlParseData.getTitle();
				bw.write("Title:" + title + "::");
				bw.newLine();
				bw.write("URL:" + url + "::");
				bw.newLine();
				bw.write(text + "::");
				bw.newLine();
				Set<WebURL> outgoingLinks = htmlParseData.getOutgoingUrls();
				bw.write("Outlinks:");

				StringBuffer sb = new StringBuffer();
				for (WebURL link : outgoingLinks) {
					String linkHref = link.getURL();
					sb.append(linkHref);
					sb.append(", ");
					if (linkHref.isEmpty())
						continue;
				}
				bw.write(sb.toString());
				bw.close();
			}

		} catch (Exception e) {
			System.out.println("Invalid path to write!!");
		}
	}
}
