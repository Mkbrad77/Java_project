package com.epita.domain.service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.epita.domain.entity.PurchaseReceipt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class PurchaseReceiptService {
    @Inject
    ElasticsearchClient elasticsearchClient;
    private static final String INDEX_NAME = "purchases";
    public void indexPurchaseReceipt(PurchaseReceipt receipt) throws IOException {
        try {
            IndexRequest<PurchaseReceipt> request = IndexRequest.of(i ->
                    i.index(INDEX_NAME)
                            .id(receipt.getPurchaseId().toString())
                            .document(receipt)
            );
            IndexResponse response = elasticsearchClient.index(request);
            System.out.println("Indexed PurchaseReceipt: " + response);
        } catch (Exception e) {
            throw e;
        }
    }


    public List<PurchaseReceipt> searchPurchasesByUser(UUID userId) throws IOException {
        SearchRequest request = SearchRequest.of(b -> b
                .index("purchases")
                .query(q -> q
                        .term(t -> t
                                .field("customerId.keyword")
                                .value(userId.toString())
                        )
                )
        );

        SearchResponse<PurchaseReceipt> response = elasticsearchClient.search(request, PurchaseReceipt.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }


    public List<PurchaseReceipt> searchReceipts(String userUuid, String productName) throws IOException {
        SearchRequest.Builder searchBuilder = new SearchRequest.Builder().index(INDEX_NAME);
        var boolQuery = new co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery.Builder();

        if (userUuid != null && !userUuid.isEmpty()) {
            boolQuery.must(TermQuery.of(t -> t.field("customerId.keyword").value(userUuid))._toQuery());
        }

        if (productName != null && !productName.isEmpty()) {
            boolQuery.must(MatchQuery.of(m -> m.field("products.name").query(productName))._toQuery());
        }

        searchBuilder.query(boolQuery.build()._toQuery());

        SearchResponse<PurchaseReceipt> response = elasticsearchClient.search(searchBuilder.build(), PurchaseReceipt.class);

        return response.hits().hits().stream()
                .map(Hit::source)
                .toList();
    }

}