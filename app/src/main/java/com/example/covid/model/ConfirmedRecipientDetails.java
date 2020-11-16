package com.example.covid.model;

public class ConfirmedRecipientDetails {
    String userId;
    String donationRequestId;
    ConfirmedRecipientDetailsData data;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDonationRequestId() {
        return donationRequestId;
    }

    public void setDonationRequestId(String donationRequestId) {
        this.donationRequestId = donationRequestId;
    }

    public ConfirmedRecipientDetails(ConfirmedRecipientDetailsData data) {
        this.data = data;
    }

    public ConfirmedRecipientDetailsData getData() {
        return data;
    }

    public void setData(ConfirmedRecipientDetailsData data) {
        this.data = data;
    }

    public static class ConfirmedRecipientDetailsData {
        String address;
        String cnic;
        String contact;
        String email;
        String id;
        String password;
        String position;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCnic() {
            return cnic;
        }

        public void setCnic(String cnic) {
            this.cnic = cnic;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
