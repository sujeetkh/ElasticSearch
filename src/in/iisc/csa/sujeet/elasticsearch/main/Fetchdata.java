package in.iisc.csa.sujeet.elasticsearch.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.iisc.csa.sujeet.elasticsearch.pojo.Person;

public class Fetchdata {
	public static void main(String[] args) {
		Node node = NodeBuilder.nodeBuilder().clusterName("elasticsearch").client(true).node();
		Client client = node.client();
		SearchResponse response = client.prepareSearch().execute().actionGet();
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		List<Person> results = new ArrayList<Person>();
		ObjectMapper mapper = new ObjectMapper();
		searchHits.forEach(hit -> {
			try {
				results.add(mapper.readValue(hit.getSourceAsString(), Person.class));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}
}
