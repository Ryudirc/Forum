package forum.board.domain;

import lombok.Data;

@Data
public class test {

    private String prodName;
    public int quantity;

    public test(String prodName, int quantity) {
        this.prodName = prodName;
        this.quantity = quantity;
    }
}
