
package com.example.jonathanmaldonado.stem_funds.stem_funds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestmentNames {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("InvestmentName")
    @Expose
    private String investmentName;
    @SerializedName("Agency")
    @Expose
    private String agency;
    @SerializedName("Subagency")
    @Expose
    private String subagency;
    @SerializedName("BriefDescription")
    @Expose
    private String briefDescription;
    @SerializedName("YearEstablished")
    @Expose
    private Integer yearEstablished;
    @SerializedName("FundingFY2008")
    @Expose
    private Float fundingFY2008;
    @SerializedName("FundingFY2009")
    @Expose
    private Float fundingFY2009;
    @SerializedName("FundingFY2010")
    @Expose
    private Float fundingFY2010;
    @SerializedName("MissionSpecificOrGeneralStem")
    @Expose
    private String missionSpecificOrGeneralStem;
    @SerializedName("AgencyOrMissionRelatedNeeds")
    @Expose
    private String agencyOrMissionRelatedNeeds;
    @SerializedName("PrimaryInvestmentObjective")
    @Expose
    private String primaryInvestmentObjective;

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

    public String getSubagency() {
        return subagency;
    }

    public void setSubagency(String subagency) {
        this.subagency = subagency;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public Integer getYearEstablished() {
        return yearEstablished;
    }

    public void setYearEstablished(Integer yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public Float getFundingFY2008() {
        return fundingFY2008;
    }

    public void setFundingFY2008(Float fundingFY2008) {
        this.fundingFY2008 = fundingFY2008;
    }

    public Float getFundingFY2009() {
        return fundingFY2009;
    }

    public void setFundingFY2009(Float fundingFY2009) {
        this.fundingFY2009 = fundingFY2009;
    }

    public Float getFundingFY2010() {
        return fundingFY2010;
    }

    public void setFundingFY2010(Float fundingFY2010) {
        this.fundingFY2010 = fundingFY2010;
    }

    public String getMissionSpecificOrGeneralStem() {
        return missionSpecificOrGeneralStem;
    }

    public void setMissionSpecificOrGeneralStem(String missionSpecificOrGeneralStem) {
        this.missionSpecificOrGeneralStem = missionSpecificOrGeneralStem;
    }

    public String getAgencyOrMissionRelatedNeeds() {
        return agencyOrMissionRelatedNeeds;
    }

    public void setAgencyOrMissionRelatedNeeds(String agencyOrMissionRelatedNeeds) {
        this.agencyOrMissionRelatedNeeds = agencyOrMissionRelatedNeeds;
    }

    public String getPrimaryInvestmentObjective() {
        return primaryInvestmentObjective;
    }

    public void setPrimaryInvestmentObjective(String primaryInvestmentObjective) {
        this.primaryInvestmentObjective = primaryInvestmentObjective;
    }

}
