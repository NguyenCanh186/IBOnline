package com.vmg.ibo.form_demand.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "DATA_SUMMARY_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataSummaryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idChildTable;
    private Long demandId;
    private Integer demandType;
    private String tags;
    private Date createdDate;
    private Long idCustomer;
    private String bondCode;
    private String bondType;
    private Long numberOfBondsWantToSell;
    private String bondTerm;
    private String interestRate;
    private String interestPaymentPeriod;
    private Long askingPrice;
    private String offeringPurpose;
    private String collateral;
    private String stocks;
    private String stockType;
    private Long numberOfStocksWantToSell;
    private Long totalCapitalMobilizationValue;
    private Date timeToRegisterToBuy;
    private Date timeToPayForPurchase;
    private Long minimumInvestmentCapital;
    private String estimatedInvestmentTime;
    private Long expectedProfitRate;
    private String levelOfRiskTolerance;
    private String investmentObjective;
    private String nameOfFund;
    private Long minimumNumberOfRegisteredForSale;
    private Long denominations;
    private Long minimumNumberOfPurchased;
    private Date validityOfRegistrationForOfferingFrom;
    private Date validityOfRegistrationForOfferingTo;
    private Date deadlineForReceivingRegistrationPaymentFrom;
    private Date deadlineForReceivingRegistrationPaymentTo;
    private String fundInvestmentFields;
    private String fundInvestmentObjective;
    private String fundInvestmentStrategy;
    private String fundBenefits;
    private String nameBond;
    private String certificateOfBondOfferingRegistration;
    private Date releaseDate;
    private Long bondValue;
    private String fundName;
    private String fundCertificateCodeWantToSell;
    private Long numberOfFundCertificateWantToSell;
    private Long desiredSellingPriceOfFundCertificates;
    private Date dayTrading;
    private String stockCode;
    private String stockName;
    private Long minimumNumberOfSharesRegisteredToBuy;
    private Long maximumNumberOfSharesRegisteredToBuy;
    private Date estimatedTransactionTime;
    private Integer status;

}
