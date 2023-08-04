package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.util.Date;
import java.util.List;

public class Order implements DataTransferObject {

  private String customerFirstName;
  private String customerLastName;
  private String customerEmail;
  private long orderId;
  private Date orderCreationDate;
  private double orderTotalDue;
  private String orderStatus;
  private String salesFirstName;
  private String salesLastName;
  private String salesEmail;
  private List<OrderLine> orderLines;

  @Override
  public long getId() {
    return orderId;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public Date getOrderCreationDate() {
    return orderCreationDate;
  }

  public void setOrderCreationDate(Date orderCreationDate) {
    this.orderCreationDate = orderCreationDate;
  }

  public double getOrderTotalDue() {
    return orderTotalDue;
  }

  public void setOrderTotalDue(double orderTotalDue) {
    this.orderTotalDue = orderTotalDue;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getSalesFirstName() {
    return salesFirstName;
  }

  public void setSalesFirstName(String salesFirstName) {
    this.salesFirstName = salesFirstName;
  }

  public String getSalesLastName() {
    return salesLastName;
  }

  public void setSalesLastName(String salesLastName) {
    this.salesLastName = salesLastName;
  }

  public String getSalesEmail() {
    return salesEmail;
  }

  public void setSalesEmail(String salesEmail) {
    this.salesEmail = salesEmail;
  }

  public List<OrderLine> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderLine> orderLines) {
    this.orderLines = orderLines;
  }

  @Override
  public String toString() {
    return "Order{"
        + "customerFirstName='" + customerFirstName + '\''
        + ", customerLastName='" + customerLastName + '\''
        + ", customerEmail='" + customerEmail + '\''
        + ", orderId=" + orderId
        + ", orderCreationDate=" + orderCreationDate
        + ", orderTotalDue=" + orderTotalDue
        + ", orderStatus='" + orderStatus + '\''
        + ", salesFirstName='" + salesFirstName + '\''
        + ", salesLastName='" + salesLastName + '\''
        + ", salesEmail='" + salesEmail + '\''
        + ", orderLines=" + orderLines
        + '}';
  }
}
