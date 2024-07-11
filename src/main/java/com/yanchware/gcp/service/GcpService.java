package com.yanchware.gcp.service;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.services.compute.Compute;

import com.google.cloud.compute.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class GcpService {

    @Autowired
    private Compute compute;

    public void provisionInstance(String zone, String project, String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        String machineType = String.format("zones/%s/machineTypes/n1-standard-1", zone);
        String sourceImage = String.format("projects/debian-cloud/global/images/family/%s", "debian-11");
        long diskSizeGb = 10L;
        String networkName = "default";
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the `instancesClient.close()` method on the client to safely
        // clean up any remaining background resources.
        try (InstancesClient instancesClient = InstancesClient.create()) {
            // Instance creation requires at least one persistent disk and one network interface.
            AttachedDisk disk =
                    AttachedDisk.newBuilder()
                            .setBoot(true)
                            .setAutoDelete(true)
                            .setType(AttachedDisk.Type.PERSISTENT.toString())
                            .setDeviceName(instanceId)
                            .setInitializeParams(
                                    AttachedDiskInitializeParams.newBuilder()
                                            .setSourceImage(sourceImage)
                                            .setDiskSizeGb(diskSizeGb)
                                            .build())
                            .build();

            // Use the network interface provided in the networkName argument.
            NetworkInterface networkInterface = NetworkInterface.newBuilder()
                    .setName(networkName)
                    .build();

            // Bind `instanceName`, `machineType`, `disk`, and `networkInterface` to an instance.
            Instance instanceResource =
                    Instance.newBuilder()
                            .setName(instanceId)
                            .setMachineType(machineType)
                            .addDisks(disk)
                            .addNetworkInterfaces(networkInterface)
                            .build();

            System.out.printf("Creating instance: %s at %s %n", instanceId, zone);

            // Insert the instance in the specified project and zone.
            InsertInstanceRequest insertInstanceRequest = InsertInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstanceResource(instanceResource)
                    .build();

            OperationFuture<Operation, Operation> operation = instancesClient.insertAsync(
                    insertInstanceRequest);

            // Wait for the operation to complete.
            Operation response = operation.get(3, TimeUnit.MINUTES);

            if (response.hasError()) {
                System.out.println("Instance creation failed ! ! " + response);
                return;
            }
            System.out.println("Operation Status: " + response.getStatus());

        }

    }

    public void deprovisionInstance(String zone, String project, String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        try (InstancesClient instancesClient = InstancesClient.create()) {

            System.out.printf("Deleting instance: %s ", instanceId);

            // Describe which instance is to be deleted.
            DeleteInstanceRequest deleteInstanceRequest = DeleteInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstance(instanceId).build();

            OperationFuture<Operation, Operation> operation = instancesClient.deleteAsync(
                    deleteInstanceRequest);
            // Wait for the operation to complete.
            Operation response = operation.get(3, TimeUnit.MINUTES);

            if (response.hasError()) {
                System.out.println("Instance deletion failed ! ! " + response);
                return;
            }
            System.out.println("Operation Status: " + response.getStatus());
        }
    }

    public void resetInstance(String zone, String project, String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        try (InstancesClient instancesClient = InstancesClient.create()) {

            ResetInstanceRequest resetInstanceRequest = ResetInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstance(instanceId)
                    .build();

            OperationFuture<Operation, Operation> operation = instancesClient.resetAsync(
                    resetInstanceRequest);
            Operation response = operation.get(3, TimeUnit.MINUTES);

            if (response.hasError()) {
                System.out.println("Operation Status: " + response.getStatus());
                return;
            }
            System.out.println("Instance reset successfully! ! " );

        }
    }

    public void startInstance(String zone, String project, String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException  {
        try (InstancesClient instancesClient = InstancesClient.create()) {

            // Create the request.
            StartInstanceRequest startInstanceRequest = StartInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstance(instanceId)
                    .build();

            OperationFuture<Operation, Operation> operation = instancesClient.startAsync(
                    startInstanceRequest);

            // Wait for the operation to complete.
            Operation response = operation.get(3, TimeUnit.MINUTES);


            if (response.hasError()) {
                System.out.println("Instance start error! ! " + response);
                return;
            }
            System.out.println("Instance started successfully! ! " );
        }
    }

    public void stopInstance(String zone, String project, String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        try (InstancesClient instancesClient = InstancesClient.create()) {

            StopInstanceRequest stopInstanceRequest = StopInstanceRequest.newBuilder()
                    .setProject(project)
                    .setZone(zone)
                    .setInstance(instanceId)
                    .build();

            OperationFuture<Operation, Operation> operation = instancesClient.stopAsync(
                    stopInstanceRequest);
            Operation response = operation.get(3, TimeUnit.MINUTES);

            if (response.hasError()) {
                System.out.println("Instance stop error! ! " + response);
                return;
            }
            System.out.println("Instance stopped successfully! ! " );
        }
    }

}