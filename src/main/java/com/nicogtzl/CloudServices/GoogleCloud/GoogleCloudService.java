package com.nicogtzl.CloudServices.GoogleCloud;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.compute.v1.*;
import com.nicogtzl.CloudServices.CloudService;
import com.nicogtzl.CloudServices.Task;

/**
 * Service class for managing Google Cloud instances, implementing CloudService interface.
 * Handles creation and destruction of Google Cloud instances based on provided Task parameters.
 *
 * Methods:
 * - createInstance(Task task): Configures and launches a new Google Cloud instance.
 *   Sets up boot disk, network interface, machine type, etc., based on Task details.
 * - destroyInstance(Task task): Deletes a specified Google Cloud instance.
 *   Requires project ID, zone, and instance name for successful operation.
 */
public class GoogleCloudService implements CloudService {

    @Override
    public void createInstance(Task task) {
        try (InstancesClient instancesClient = InstancesClient.create()) {
            // Configuración del disco de arranque y la imagen
            AttachedDisk bootDisk = AttachedDisk.newBuilder()
                    .setBoot(true)
                    .setAutoDelete(true) // Disco se borra automáticamente al eliminar la instancia
                    .setInitializeParams(AttachedDiskInitializeParams.newBuilder()
                            .setSourceImage(task.getImage()) // La imagen debe ser la ruta completa de la imagen, por ejemplo: "projects/debian-cloud/global/images/family/debian-10"
                            .build())
                    .build();

            // Configuración de la red
            NetworkInterface networkInterface = NetworkInterface.newBuilder()
                    .setName("global/networks/default") // Usa el nombre de la red VPC que deseas conectar
                    .build();

            // Crear la instancia de GCP con el disco de arranque y la configuración de red
            Instance instance = Instance.newBuilder()
                    .setName(task.getName())
                    .setMachineType(String.format("zones/%s/machineTypes/%s", task.getZone(), task.getSize()))
                    .addDisks(bootDisk)
                    .addNetworkInterfaces(networkInterface)
                    // ... otros ajustes para la instancia ...
                    .build();

            // Configurar la solicitud de inserción
            OperationFuture<Operation, Operation> insertOperation = instancesClient.insertAsync(InsertInstanceRequest.newBuilder()
                    .setProject(task.getProjectId()) // Asegúrate de que task.getProjectId() obtiene el ID del proyecto correcto
                    .setZone(task.getZone()) // Asegúrate de que task.getZone() obtiene la zona correcta
                    .setInstanceResource(instance)
                    .build());

            // Esperar a que la operación se complete
            Operation response = insertOperation.get();
            System.out.println("Instance created: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroyInstance(Task task) {
        try (InstancesClient instanceClient = InstancesClient.create()) {
            OperationFuture<Operation, Operation> deleteOperation = instanceClient.deleteAsync(
                    task.getProjectId(), task.getZone(), task.getSessionId());

            Operation response = deleteOperation.get();
            System.out.println("Instance deleted: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
