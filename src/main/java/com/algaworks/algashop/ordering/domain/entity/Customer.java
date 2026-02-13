package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import com.algaworks.algashop.ordering.domain.vo.CustomerId;
import com.algaworks.algashop.ordering.domain.vo.FullName;
import com.algaworks.algashop.ordering.domain.vo.LoyaltyPoints;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer implements Serializable {
    private CustomerId id;
    private FullName fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;

    public Customer(FullName fullName, CustomerId id, LocalDate birthDate, String email, String phone, String document,
                    Boolean promotionNotificationAllowed, OffsetDateTime registeredAt) {
        this.setFullName(fullName);
        this.setId(id);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationAllowed(promotionNotificationAllowed);
        this.setRegisteredAt(registeredAt);
        this.archived = false;
        this.setLoyaltyPoints(LoyaltyPoints.ZERO);
    }

    public Customer(CustomerId id, FullName fullName, LocalDate birthDate, String email, String phone, String document,
                    Boolean promotionNotificationAllowed, Boolean archived, OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt, LoyaltyPoints loyaltyPoints) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationAllowed(promotionNotificationAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
    }

    public void addLoyaltyPoints(LoyaltyPoints loyaltyPoints){
        verifyIfChangeable();
        this.setLoyaltyPoints(this.loyaltyPoints().add(loyaltyPoints));
    }

    public void archive(){
        verifyIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous","Anonymous"));
        this.setPhone("000-000-0000");
        this.setDocument("00-000-0000");
        this.setEmail(UUID.randomUUID().toString().concat("@gmail.com"));
        this.setBirthDate(null);
        this.setPromotionNotificationAllowed(false);
    }

    public void enablePromotionNotifications(){
        verifyIfChangeable();
        this.setPromotionNotificationAllowed(true);
    }

    public void disablePromotionNotifications(){
        verifyIfChangeable();
        this.setPromotionNotificationAllowed(false);
    }

    public void changeEmail(String newEmail){
        verifyIfChangeable();
        setEmail(newEmail);
    }

    public void changePhone(String newPhone){
        verifyIfChangeable();
        setPhone(newPhone);
    }

    public void changeName(FullName newName){
        verifyIfChangeable();
        setFullName(newName);
    }

    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setPromotionNotificationAllowed(Boolean promotionNotificationAllowed) {
        Objects.requireNonNull(promotionNotificationAllowed, "promotionNotificationAllowed cannot be null");
        this.promotionNotificationAllowed = promotionNotificationAllowed;
    }

    private void setEmail(String email) {
        FieldValidations.requiresValidEmail(email, ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID);
        this.email = email;
    }

    private void setPhone(String phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, ErrorMessages.VALIDATION_ERROR_FULLNAME_IS_NULL);
        this.fullName = fullName;
    }

    private void setBirthDate(LocalDate birthDate) {
        if(birthDate == null){
            this.birthDate = null;
            return;
        }

        if (birthDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }

        this.birthDate = birthDate;
    }

    private void setDocument(String document) {
        Objects.requireNonNull(document);
        this.document = document;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived, "archived cannot be null");
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        this.loyaltyPoints = loyaltyPoints;
    }

    private void verifyIfChangeable() {
        if(this.archived){
            throw new CustomerArchivedException(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);
        }
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String document() {
        return document;
    }

    public Boolean promotionNotificationAllowed() {
        return promotionNotificationAllowed;
    }

    public Boolean archived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
