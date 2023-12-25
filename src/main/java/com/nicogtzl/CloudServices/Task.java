package com.nicogtzl.CloudServices;

/**
 * Represents a task for cloud service operations, particularly for creating or destroying instances.
 * This class encapsulates the details required for these operations across various cloud platforms.
 *
 * Fields include API key, project ID (for Google Cloud), instance name, region, zone, size, image, and task type.
 *
 * The class provides setters and getters for each field, allowing manipulation of these properties as needed.
 *
 * Usage:
 * - Construct an instance with necessary details for the intended cloud operation.
 * - Utilize setters to modify task parameters as needed before execution.
 */
public class Task {
    private String apiKey;
    private String projectId; // ID del proyecto de Google Cloud
    private String name;
    private String region;
    private String zone;
    private String size;
    private String image;
    private boolean isDestroyTask;
    private String sessionId; // Used to identify the droplet to destroy


    // Constructor actualizado con projectId
    public Task(String apiKey, String projectId, String name, String region, String zone, String size, String image, boolean isDestroyTask) {
        this.apiKey = apiKey;
        this.projectId = projectId; // Guardar projectId
        this.name = name;
        this.region = region;
        this.zone = zone;
        this.size = size;
        this.image = image;
        this.isDestroyTask = isDestroyTask;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }


    public void setSize(String size) {
        this.size = size;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIsDestroyTask(boolean isDestroyTask) {
        this.isDestroyTask = isDestroyTask;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getZone() {
        return zone;
    }

    public String getSize() {
        return size;
    }

    public String getImage() {
        return image;
    }

    public boolean isDestroyTask() {
        return isDestroyTask;
    }

    public String getSessionId() {
        return sessionId;
    }


}
