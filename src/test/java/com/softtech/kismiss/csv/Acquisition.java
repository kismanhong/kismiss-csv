package com.softtech.kismiss.csv;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.softtech.kismiss.csv.annotations.CsvColumn;

public class Acquisition {
	public static final String[] MAINTENANCE_LIST_FIELDS = {"inputDate", "agentId", "agentName", 
		"region", "area", "gpsLocation", "referralNo", "accountNo", "customerName",
		"marketingCode","status","description"};

	public static final String INPUT_DATE = "inputDate";
	
	
	private int no;

	
	@CsvColumn(position = 0, header = "No", index = true)
	private Long id;
	
	
	@CsvColumn(position = 1, header = "Tanggal submit form")
	private Date inputDate;
	
	
	@CsvColumn(position = 2, header = "ID Agen")
	private String agentId;
	
	
	@CsvColumn(position = 3, header = "Nama Agen")
	private String agentName;
	
	
	@CsvColumn(position = 4, header = "Region")
	private String region;
	
	
	@CsvColumn(position = 5, header = "Area")
	private String area;
	
	
	@CsvColumn(position = 6, header = "Lokasi GPS")
	private String gpsLocation;
	
	
	@CsvColumn(position = 7, header = "Referral No")
	private String referralNo;
	
	
	@CsvColumn(position = 8, header = "No Rekening")
	private String accountNo;
	
	
	@CsvColumn(position = 9, header = "Nama Nasabah")
	private String customerName;
	
	
	@CsvColumn(position = 10, header = "Marketing Code")
	private String marketingCode;
	
	
	@CsvColumn(position = 11, header = "Status")
	private String status;
	
	
	private String description;

	@CsvColumn(position = 12, header = "Keterangan")
	private String descriptionCsv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}


	public String getReferralNo() {
		return referralNo;
	}

	public void setReferralNo(String referralNo) {
		this.referralNo = referralNo;
	}


	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	public String getMarketingCode() {
		return marketingCode;
	}

	public void setMarketingCode(String marketingCode) {
		this.marketingCode = marketingCode;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionCsv() {
		if (!StringUtils.isEmpty(description)) {
			descriptionCsv = description.replace("\n", "");
		} else {
			descriptionCsv = "";
		}
		return descriptionCsv;
	}

}
