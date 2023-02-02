package com.akhianand.springrolejwt.es.service;

import com.akhianand.springrolejwt.es.dao.UserElkDao;
import com.akhianand.springrolejwt.es.model.UserElk;
import com.akhianand.springrolejwt.es.utils.HelperFunctions;
import com.akhianand.springrolejwt.es.utils.ResultQuery;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchService {

    @Value("${api.elasticsearch.uri}")
    private String elasticSearchUri;

    @Value("${api.elasticsearch.search}")
    private String elasticSearchSearchPrefix;
   

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);
    @Autowired
    private UserElkDao userElkRepo;



//    private final RoleElkDao roleElkRepo;
//    private final PrivilegeElkDao privilegeElkRepo;




    public ResultQuery searchFromQuery(String query) throws IOException {
        String body = HelperFunctions.buildMultiIndexMatchBody(query);
        return executeHttpRequest(body);
    }


    /**
     * Fetch resultQuery from elastic engine for the given body
     *
     * @param body String
     * @return ResultQuery
     * @throws IOException IOException
     */
    private ResultQuery executeHttpRequest(String body) throws IOException{
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ResultQuery resultQuery = new ResultQuery();
            HttpPost httpPost = new HttpPost(HelperFunctions.buildSearchUri(elasticSearchUri
                    , "", elasticSearchSearchPrefix));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            try {
                httpPost.setEntity(new StringEntity(body, "UTF-8"));
                HttpResponse response = httpClient.execute(httpPost);
                String message = EntityUtils.toString(response.getEntity());
                JSONObject myObject = new JSONObject(message);
                myObject.getJSONObject("hits")
                        .getJSONObject("total");
                resultQuery
                        .setElements((myObject
                                .getJSONObject("hits")).toString());
                resultQuery
                        .setNumberOfResults(myObject.getJSONObject("hits")
                                .getJSONObject("total") + "");
                resultQuery.setTimeTook((float) ((double) myObject.getInt("took") / 1000));
            } catch (IOException | JSONException e) {
                LOGGER.error("Error while connecting to elastic engine --> {}", e.getMessage());
                resultQuery.setNumberOfResults("0");
            }
            return resultQuery;
        }
    }

    public Iterable<UserElk> findAll() {
        return userElkRepo.findAll();
    }

    public UserElk findById(Long id) {
        return userElkRepo.findById(id).orElse(null);
    }

    public List<UserElk> search(String name)
    {
        List<UserElk> fName = userElkRepo.searchUserElkByFirstNameOrLastName(name);
//        List<UserElk> lName = userElkRepo.searchUserElkByLastName(name);
//        if (fName.isEmpty())
//        {
//            return lName;
//        }
//        else {
            return fName;
    }

}
