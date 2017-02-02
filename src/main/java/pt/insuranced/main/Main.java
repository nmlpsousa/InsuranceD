package pt.insuranced.main;

import pt.insuranced.sdk.enums.ClaimStatusEnum;

public class Main {
    public static void main(String[] args) {
        ClaimStatusEnum claimStatusEnum = ClaimStatusEnum.getClaimStatusByCode(2);

        System.out.println(claimStatusEnum);
    }
}
