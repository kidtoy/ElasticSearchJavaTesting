package Insert;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import javafx.scene.NodeBuilder;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


public class main {

    private static String url = "kenji.platform.semantixsense.com.br";
    private static int port = 9200;
//    private static boolean sniff = false;
//    private static boolean ignore_cluster_name = false;
//    private static String ping_timeout = "5s";
//    private static String nodes_sampler_interval = "5s";
//    private static String cluster_name = "docker-cluster";

    public static void main(String[] args){
        deleteBySearch();
    }

    private static void deleteBySearch(){

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(url, port, "http")));

        MeuListener<BulkByScrollResponse> listener = new MeuListener<>();

        DeleteByQueryRequest request =
                new DeleteByQueryRequest("iotplatform_alarm");
        request.setQuery(QueryBuilders.termQuery("id","testeeeee"));

        client.deleteByQueryAsync(request,RequestOptions.DEFAULT, listener);
        System.out.println(listener.waitAndRetrieve().getDeleted());
    }

    private static void sendResponse(){
        try {

            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(url, port, "http")));

            MeuListener<IndexResponse> listener = new MeuListener<IndexResponse>() {
            };

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("id", "testeeeee");
            jsonMap.put("tenantId", "dhenny");
            jsonMap.put("originatorId", "alguem");
            jsonMap.put("originatorType", "ALARM");
            jsonMap.put("type", "ALARM");
            jsonMap.put("severity", "CRITICAL");
            jsonMap.put("status", "ANY");
            jsonMap.put("startTs", 21);
            jsonMap.put("endTs", 22);
            jsonMap.put("ackTs", 23);
            jsonMap.put("clearTs", 24);
            jsonMap.put("details", "Teste");
            jsonMap.put("propagate", false);
            IndexRequest indexRequest = new IndexRequest("iotplatform_alarm", "data").source(jsonMap);

            client.indexAsync(indexRequest,RequestOptions.DEFAULT, listener);

            System.out.println(listener.waitAndRetrieve().getShardInfo().toString());

            System.out.println("Ayoooooooooooooooooooooooooooooooo");


            client.close();
        }catch (Exception e){
            System.out.println("Unknown host exception"+e.toString());
        }

        System.out.println("Cabou");
    }

    private static void getResponse(){
        try {

            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(url, port, "http")));

            MeuFuture listener = new MeuFuture();

            SearchRequest searchRequest = new SearchRequest();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            BoolQueryBuilder qb = QueryBuilders.boolQuery()
            .must(QueryBuilders.termQuery("id","testeiot"))
            .must(QueryBuilders.termQuery("tenant_id","dhenny"));

            searchSourceBuilder.query(qb);
            searchRequest.source(searchSourceBuilder);

            client.searchAsync(searchRequest,RequestOptions.DEFAULT, listener);

            ListenableFuture<SearchResponse> lister = transform(listener);

            System.out.println("Ayoooooooooooooooooooooooooooooooo");
            SearchResponse SR = lister.get();

            System.out.println(SR.getHits().getHits()[0].getSourceAsMap());


            client.close();
        }catch (Exception e){
            System.out.println("Unknown host exception"+e.toString());
        }

        System.out.println("Cabou");
    }

    private static ListenableFuture<SearchResponse> transform(MeuListener<SearchResponse> listener){
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<SearchResponse> teste = service.submit(() -> {
            SearchResponse response  = listener.waitAndRetrieve();
            System.out.println("After Sleep");
            return response != null ? response : null;
        } );
        service.shutdown();
        return teste;
    }



}
