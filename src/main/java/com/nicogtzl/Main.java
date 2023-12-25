package com.nicogtzl;

import com.nicogtzl.CloudServices.CloudService;
import com.nicogtzl.CloudServices.DigitalOcean.DigitalOceanService;
import com.nicogtzl.CloudServices.GoogleCloud.GoogleCloudService;
import com.nicogtzl.CloudServices.Task;

/**
 * Main class for managing cloud instances across different providers.
 * Supports creating and destroying instances on DigitalOcean and Google Cloud.
 *
 * Usage:
 * - To create an instance: Main <provider> <token> create <additional parameters>
 * - To destroy an instance: Main <provider> <token> destroy <additional parameters>
 *
 * Providers:
 * - digitalocean
 * - googlecloud
 *
 * Actions:
 * - create
 * - destroy
 *
 * Additional parameters vary based on the action and provider.
 */

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: Main <provider> <token> <action> [<additional parameters>]");
            System.out.println("Providers: 'digitalocean', 'googlecloud'");
            System.out.println("Available actions: 'create', 'destroy'");
            return;
        }

        String provider = args[0];
        String apiToken = args[1];
        String action = args[2];

        CloudService cloudService = provider.equals("digitalocean") ? new DigitalOceanService() : new GoogleCloudService();

        switch (action) {
            case "create":
                if (provider.equals("digitalocean") && args.length < 7) {
                    System.out.println("To create a Droplet, provide: name, region, size, and image.");
                    return;
                } else if (provider.equals("googlecloud") && args.length < 8) {
                    System.out.println("To create a Google Cloud instance, provide: project_id, name, region, zone, size, and image.");
                    return;
                }

                String projectId = provider.equals("googlecloud") ? args[3] : null;
                int startIndex = provider.equals("googlecloud") ? 4 : 3;

                Task createTask = new Task(apiToken, projectId, args[startIndex], args[startIndex + 1], args[startIndex + 2], args[startIndex + 3], args[startIndex + 4], false);
                if (provider.equals("googlecloud")) {
                    createTask.setZone(args[startIndex + 2]);
                }

                cloudService.createInstance(createTask);
                break;

            case "destroy":
                if (provider.equals("googlecloud") && args.length < 5) {
                    System.out.println("To destroy a Google Cloud instance, provide: project_id, zone, and instance_id.");
                    return;
                }
                String destroyProjectId = provider.equals("googlecloud") ? args[3] : null;
                String destroyZone = provider.equals("googlecloud") ? args[4] : null;
                String destroySessionId = provider.equals("googlecloud") ? args[5] : args[3];
                Task destroyTask = new Task(apiToken, destroyProjectId, null, null, destroyZone, null, null, true);
                destroyTask.setSessionId(destroySessionId);

                cloudService.destroyInstance(destroyTask);
                break;

            default:
                System.out.println("Unrecognized action. Available actions are 'create' and 'destroy'.");
                break;
        }
    }
}
