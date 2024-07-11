package com.yanchware.gcp.controller;


import com.yanchware.gcp.service.GcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/gcp")
public class ServiceInstanceController {

    @Autowired
    private GcpService gcpService;

    @PostMapping("/{zone}/{project}/{instanceId}")
    public ResponseEntity<String> provision(@PathVariable String zone, @PathVariable String project, @PathVariable String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        gcpService.provisionInstance(zone,project,instanceId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Instance provisioned successfully.");
    }

    @PostMapping("/{zone}/{project}/{instanceId}/{action}")
    public ResponseEntity<String> actions(@PathVariable String zone, @PathVariable String project, @PathVariable String instanceId,@PathVariable String action) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        switch (action){
            case "start":
                gcpService.startInstance(zone,project,instanceId);
                break;
            case "stop":
                gcpService.stopInstance(zone,project,instanceId);
                break;
            case "reset":
                gcpService.resetInstance(zone,project,instanceId);
                break;
            default:
                return ResponseEntity.status(HttpStatus.CREATED).body("Instance action couldn't found. Try: start, stop, restart");

        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Instance "+action+" action completed successfully.");
    }

    @PutMapping("/{zone}/{project}/{instanceId}")
    public ResponseEntity<String> reprovision(@PathVariable String zone, @PathVariable String project, @PathVariable String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        gcpService.resetInstance(zone,project,instanceId);
        return ResponseEntity.status(HttpStatus.OK).body("Instance reprovisioned successfully.");
    }

    @DeleteMapping("/{zone}/{project}/{instanceId}")
    public ResponseEntity<String> deprovision(@PathVariable String zone, @PathVariable String project, @PathVariable String instanceId) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        gcpService.deprovisionInstance(zone,project,instanceId);
        return ResponseEntity.status(HttpStatus.OK).body("Instance deprovisioned successfully.");
    }

}