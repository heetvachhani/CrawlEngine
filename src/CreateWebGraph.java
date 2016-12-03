import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CreateWebGraph {
	public static String webGraphDirectory = "/Users/Heet/Downloads/soccerData/indexInfo/";
	private static String path = "/Users/Heet/Downloads/soccerData/soccerData/documents";
	private static HashMap<String, WebPage> webPages = new HashMap<>();

	public static void main(String[] args) throws Exception {
		File[] files = new File(path).listFiles();
		for (File f : files) {
			if (f.isFile()) {
				getOutgoingLinks(f);
			}
		}
		writeWebGraph(webPages);

	}

	private static void getOutgoingLinks(File f) throws IOException {
		int docId = Integer.parseInt(f.getName());
		WebPage pageObj = new WebPage();
		pageObj.docId = docId;
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = "";
		String url = "";

		while ((line = br.readLine()) != null) {
			if (line.contains("URL:")) {
				url = line.replaceAll("URL:", "");
				url = url.replaceAll("::", "");
				// System.out.println(url);
				pageObj.url = url;
			} else if (line.contains("Outlinks")) {
				webPages.put(url, pageObj);
				String links[] = line.split(", ");
				links[0] = links[0].replaceAll("Outlinks:", "");
				for (String link : links) {
					if (link.isEmpty())
						continue;
					WebPage outPage;
					if (webPages.containsKey(link)) {
						outPage = webPages.get(link);
						outPage.inLinks.put(url, 1 + (outPage.inLinks.get(url) == null ? 0 : outPage.inLinks.get(url)));
					}
					pageObj.outLinks.put(link,
							1 + (pageObj.outLinks.get(link) == null ? 0 : pageObj.outLinks.get(link)));
				}
			}

		}
		// System.out.println("=======================");
		br.close();
	}

	private static void writeWebGraph(HashMap<String, WebPage> webPages) throws Exception {
		File file = new File(webGraphDirectory + "webgraph2.gf");
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		for (String url : webPages.keySet()) {
			WebPage page = webPages.get(url);
			bw.write(String.valueOf(page.docId));
			bw.write("\t");
			bw.write(page.url);
			bw.write("\t");
			bw.write(":INLINKS:");
			bw.write(String.valueOf(page.inLinks.size()));
			bw.write("\t");
			for (String inUrl : page.inLinks.keySet()) {
				bw.write(inUrl);
				bw.write("\t");
			}
			bw.write(":OUTLINKS:");
			bw.write(String.valueOf(page.outLinks.size()));
			bw.write("\t");
			for (String inUrl : page.outLinks.keySet()) {
				bw.write(inUrl);
				bw.write("\t");
			}
			bw.newLine();
		}
		bw.close();
	}
}

class WebPage {
	String url;
	Integer docId;
	HashMap<String, Integer> inLinks = new HashMap<String, Integer>();
	HashMap<String, Integer> outLinks = new HashMap<String, Integer>();

	public boolean equals(Object obj) {
		if (!(obj instanceof WebPage))
			return false;
		return (url.equals(((WebPage) obj).url));
	}

	public int hashCode() {
		return url.hashCode();
	}
}
