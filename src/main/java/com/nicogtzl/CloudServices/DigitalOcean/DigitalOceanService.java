package com.nicogtzl.CloudServices.DigitalOcean;

import com.nicogtzl.CloudServices.CloudService;
import com.nicogtzl.CloudServices.Task;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Service class for managing DigitalOcean instances, implementing the CloudService interface.
 * Handles the creation and destruction of DigitalOcean droplets based on provided Task parameters.
 *
 * Methods:
 * - createInstance(Task task): Sets up and sends a request to DigitalOcean's API to create a new droplet.
 *   Configures droplet details such as name, region, size, image, and SSH keys based on Task properties.
 * - destroyInstance(Task task): Sends a request to DigitalOcean's API to delete a specified droplet.
 *   Requires the droplet ID for successful operation and handles the response from the API.
 */
public class DigitalOceanService implements CloudService {

    @Override
    public void createInstance(Task task) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.digitalocean.com/v2/droplets");
            request.setHeader("Authorization", "Bearer " + task.getApiKey());
            request.setHeader("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("name", task.getName());
            json.put("region", task.getRegion());
            json.put("size", task.getSize());
            json.put("image", task.getImage());

            // Añadir la clave SSH (ajustar según sea necesario)
            JSONArray sshKeys = new JSONArray();
            sshKeys.put("37305752"); // Id de la cuenta de DigitalOcean
            json.put("ssh_keys", sshKeys);

            StringEntity requestEntity = new StringEntity(json.toString());
            request.setEntity(requestEntity);

            String responseString = EntityUtils.toString(client.execute(request).getEntity(), "UTF-8");
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroyInstance(Task task) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete("https://api.digitalocean.com/v2/droplets/" + task.getSessionId());
            request.setHeader("Authorization", "Bearer " + task.getApiKey());
            request.setHeader("Content-Type", "application/json");

            CloseableHttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 204) {
                System.out.println("Droplet successfully destroyed.");
            } else {
                System.out.println("Error destroying droplet. Status code: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}