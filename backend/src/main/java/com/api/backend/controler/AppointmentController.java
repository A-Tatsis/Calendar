package com.api.backend.controler;

import com.api.backend.models.Appointments;
import com.api.backend.models.ClosedAppointment;
import com.api.backend.models.Subscription;
import com.api.backend.models.UserSubscriptions;
import com.api.backend.resources.*;
import com.api.backend.service.AppointmentService;
import com.api.backend.service.ClosedAppointmentService;
import com.api.backend.service.SubscriptionService;
import com.api.backend.service.UserSubscriptionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private AppointmentService appointmentService;

    private SubscriptionService subscriptionService;

    private UserSubscriptionsService userSubscriptionsService;

    private ClosedAppointmentService closedAppointmentService;

    public AppointmentController(AppointmentService appointmentService, SubscriptionService subscriptionService, UserSubscriptionsService userSubscriptionsService, ClosedAppointmentService closedAppointmentService) {
        this.appointmentService = appointmentService;
        this.subscriptionService = subscriptionService;
        this.userSubscriptionsService = userSubscriptionsService;
        this.closedAppointmentService = closedAppointmentService;
    }

//    Get the data from Appointment table
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResource>> retrieveAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAll().stream().map(AppointmentResource::new).toList());
    }

    @GetMapping("appointmentwithid")
//    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<List<AppointmentResourceId>> getAllAppointmentsWithId() {
        return ResponseEntity.ok(appointmentService.findAll().stream().map(AppointmentResourceId::new).toList());
    }

//    Add a session generate id, name, date, numberOfUsers, Teacher, Status  -->  Table appointment
    @PostMapping("/appointments")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<AppointmentResource> createAppointment(@Validated @RequestBody AppointmentResource request) {
        var newAppointment = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResource(newAppointment));
    }

//    Get specific appointment
    @GetMapping("/appointment/{id}")
    public ResponseEntity<AppointmentResource> getAppointmentById(@PathVariable UUID id) {
        Appointments appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(new AppointmentResource(appointment));

    }

//    Delete a row from Appointment table
    @DeleteMapping("/appointment/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<AppointmentResource> deleteAppointmentById(@PathVariable UUID id) {
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.noContent().build();
    }

//    Change a row from Appointment table
    @PutMapping("/appointment/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<AppointmentResource> updateAppointment(@PathVariable UUID id, @Validated @RequestBody AppointmentResource request) {
        var appointment = appointmentService.updateAppointment(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AppointmentResource(appointment));
    }

//    Get the data from Sub table
    @GetMapping("/subscription")
    public ResponseEntity<List<SubscriptionResource>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.findAll().stream().map(SubscriptionResource::new).toList());
    }

    @GetMapping("/subscriptionids")
    public ResponseEntity<List<SubscriptionResourceId>> getAllSubsctiptionsWithId() {
        return ResponseEntity.ok(subscriptionService.findAll().stream().map(SubscriptionResourceId::new).toList());
    }

//    Add new subscription to the table Sub
    @PostMapping("/subscription")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<SubscriptionResource> createSubsctiption(@Validated @RequestBody SubscriptionResource request) {
        var newSubsctiption = subscriptionService.createSubscription(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SubscriptionResource(newSubsctiption));
    }

//    Get specific subscription
    @GetMapping("/subscription/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable UUID id) {
        return ResponseEntity.ok(subscriptionService.findById(id));
    }

//    Delete a row from Sub table
    @DeleteMapping("/subscription/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<SubscriptionResource> deleteSubscription(@PathVariable UUID id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

//    Change a row from Sub table
    @PutMapping("/subscription/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<SubscriptionResource> updateSubscrption(@PathVariable UUID id, @Validated @RequestBody SubscriptionResource request){
        var subscription = subscriptionService.updateSubscription(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SubscriptionResource(subscription));
    }

//    Get Subscriptions of users
    @GetMapping("/user/subscription")
    public ResponseEntity<List<UserSubscriptionsResource>> getAllUserSubscriptions(){
        return ResponseEntity.ok(userSubscriptionsService.findAll().stream().map(UserSubscriptionsResource::new).toList());
    }

//    Get a specific Subscription user based on the id
    @GetMapping("/user/subscription/{id}")
    public ResponseEntity<UserSubscriptionsResource> getSpecificUserSubscription(@PathVariable UUID id){
        UserSubscriptions userSubscriptions = userSubscriptionsService.findById(id);
        return ResponseEntity.ok(new UserSubscriptionsResource(userSubscriptions));
    }

//    Insert a new user subscription
    @PostMapping("/user/subscription")
    public ResponseEntity<UserSubscriptionsResource> createUserSubscription(@Validated @RequestBody UserSubscriptionsResource request) {
        var newUserSubscription = userSubscriptionsService.createUserSubscriptions(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserSubscriptionsResource(newUserSubscription));
    }

//    Delete a specific Subscription user by id
    @DeleteMapping("/user/subscription/{id}")
    public ResponseEntity<UserSubscriptionsResource> deleteUserSubscription(@PathVariable UUID id) {
        userSubscriptionsService.deleteUserSubscription(id);
        return ResponseEntity.noContent().build();
    }

//    Change a Subscription user based on id
    @PutMapping("/user/subscription/{id}")
    public ResponseEntity<UserSubscriptionsResource> updateUserSubscription(@PathVariable UUID id, @Validated @RequestBody UserSubscriptionsResource request) {
        var userSubscription = userSubscriptionsService.updateUserSubscription(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserSubscriptionsResource(userSubscription));
    }

//    Get all the closed appointments
    @GetMapping("/closedappointment")
    public ResponseEntity<List<ClosedAppointmentResource>> getAllClosedAppointment() {
        return ResponseEntity.ok(closedAppointmentService.findAll().stream().map(ClosedAppointmentResource::new).toList());
    }

//    Get a specific closed appointment
    @GetMapping("/closedappointment/{id}")
    public ResponseEntity<ClosedAppointmentResource> getSpecificClosedAppointment(@PathVariable UUID id){
        ClosedAppointment closedAppointment = closedAppointmentService.findById(id);
        return ResponseEntity.ok(new ClosedAppointmentResource(closedAppointment));
    }

//    Get all the appointments of the loging user
    @GetMapping("/closedappointment/user")
    public ResponseEntity<List<ClosedAppointmentResource>> getAllClosedAppointmentByUser() {
        return ResponseEntity.ok(closedAppointmentService.findByUser().stream().map(ClosedAppointmentResource::new).toList());
    }

//    Get the number of users that have been in a specific appointment
    @GetMapping("/closedappointment/appointment/{id}/status-counts")
    public ResponseEntity<Map<Integer, Long>> countSpecificAppointmentClosedAppointment(@PathVariable UUID id) {
        Map<Integer, Long> counts = closedAppointmentService.countSpecificAClosedAppointment(id);
        return ResponseEntity.ok(counts);
    }

//    Get the number of appointment that a specific user has
    @GetMapping("/closedappointment/user/{id}/status-counts")
    public ResponseEntity<Map<Integer, Long>> countSpecificUserClosedAppointment(@PathVariable UUID id) {
        Map<Integer, Long> counts = closedAppointmentService.countSpecificUClosedAppointment(id);
        return ResponseEntity.ok(counts);
    }

//    The user registers in the specific appointment  // TODO maybe give the opportunity to the admin to add a user to a specific appointment
    @PostMapping("/closedappointment/appointment")
    public ResponseEntity<ClosedAppointmentResource> createClosedAppointment(@Validated @RequestBody ClosedAppointmentResource request) {
        var closedAppointment = closedAppointmentService.createClosedAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ClosedAppointmentResource(closedAppointment));
    }

//    user canceled the appointment and update the status of the latest user that have been in status 2=waiting
    @PostMapping("/closedappointment/appointment/{id}/canceled")
    public ResponseEntity<ClosedAppointmentResource> canceledClosedAppointment(@PathVariable UUID id) {
        closedAppointmentService.canceledAppointment(id);
        return ResponseEntity.noContent().build();
    }

}