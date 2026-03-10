package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import com.algaworks.algashop.ordering.domain.vo.*;
import lombok.Builder;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer implements Serializable {
    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;
    private Address address;

    @Builder(builderClassName = "CreateNewCustomerBuild", builderMethodName = "createNew")
    private static Customer newCustomer(FullName fullName,BirthDate birthDate, Email email, Phone phone,
                                     Document document, Boolean promotionNotificationAllowed, Address address) {
        return new Customer(
                new CustomerId(),
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationAllowed,
                false,
                OffsetDateTime.now(),
                null,
                LoyaltyPoints.ZERO,
                address
        );
    }

    @Builder(builderClassName = "ExistingCustomerBuild", builderMethodName = "existing")
    public Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone, Document document,
                    Boolean promotionNotificationAllowed, Boolean archived, OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt, LoyaltyPoints loyaltyPoints, Address address) {
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
        this.setAddress(address);
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
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("00-000-0000"));
        this.setEmail(new Email(UUID.randomUUID().toString().concat("@gmail.com")));
        this.setBirthDate(null);
        this.setPromotionNotificationAllowed(false);
        this.setAddress(
                this.address().toBuilder()
                        .number("Anonymized")
                        .complement(null)
                        .build()
        );
    }

    public void enablePromotionNotifications(){
        verifyIfChangeable();
        this.setPromotionNotificationAllowed(true);
    }

    public void disablePromotionNotifications(){
        verifyIfChangeable();
        this.setPromotionNotificationAllowed(false);
    }

    public void changeEmail(Email newEmail){
        verifyIfChangeable();
        setEmail(newEmail);
    }

    public void changePhone(Phone newPhone){
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

    private void setEmail(Email email) {
        this.email = email;
    }

    private void setPhone(Phone phone) {
        this.phone = phone;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, ErrorMessages.VALIDATION_ERROR_FULLNAME_IS_NULL);
        this.fullName = fullName;
    }

    private void setBirthDate(BirthDate birthDate) {
        if (birthDate == null) {
            this.birthDate = null;
            return;
        }
        this.birthDate = birthDate;
    }

    private void setDocument(Document document) {
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

    public void setAddress(Address address) {
        Objects.requireNonNull(address);
        this.address = address;
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

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
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

    public Address address() {
        return address;
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
