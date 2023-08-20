package spa.dto;

public class ItemDTO {
    private String code;
    private String description;
    private String qty;
    private String unit_price;


    public ItemDTO() {
    }

    public ItemDTO(String code, String description, String qty, String unit_price) {
        this.code = code;
        this.description = description;
        this.unit_price = unit_price;
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
