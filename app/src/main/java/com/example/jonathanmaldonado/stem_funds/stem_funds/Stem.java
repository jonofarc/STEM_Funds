package com.example.jonathanmaldonado.stem_funds.stem_funds;


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Stem {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("InvestmentName")
    @Expose
    private String investmentName;
    @SerializedName("Agency")
    @Expose
    private String agency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvestmentName() {
        return investmentName;
    }

    public void setInvestmentName(String investmentName) {
        this.investmentName = investmentName;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

}