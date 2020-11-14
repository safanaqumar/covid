package com.example.covid.model;

public class DonorRequestModel {
    String donationRequestId;
    String donorId;
    DonorRequestDataModel getData;

    public DonorRequestModel(DonorRequestDataModel getData) {
        this.getData = getData;
    }

    public DonorRequestDataModel getGetData() {
        return getData;
    }

    public void setGetData(DonorRequestDataModel getData) {
        this.getData = getData;
    }

    public static class DonorRequestDataModel{
        String confirm;
        String message;
        String name;


        public String getConfirm() {
            return confirm;
        }

        public void setConfirm(String confirm) {
            this.confirm = confirm;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public String getDonationRequestId() {
        return donationRequestId;
    }

    public void setDonationRequestId(String donationRequestId) {
        this.donationRequestId = donationRequestId;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

}
