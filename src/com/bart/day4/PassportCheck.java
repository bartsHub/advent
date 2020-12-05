package com.bart.day4;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PassportCheck {

    public static void main(String... args) {
        PassportCheck cp = new PassportCheck();
        try {
            // process the input file
            List<String> data = cp.processPassportfile();
            // create passport details list
            List<PassportDetail> passportList = createPassportDetailList(data);
            // validate
            int count = validatePassportList(passportList);
            System.out.println("Good Passports:" + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int validatePassportList(List<PassportDetail> passportList) {
        System.out.println("Total Passports: " + passportList.size());
        int goodDetails = 0;
        int badDetails = 0;
        for (PassportDetail details : passportList) {
            if (details.validate()) {
                goodDetails++;
            } else {
                badDetails++;
            }
        }
        System.out.println("Bad Passports: " + badDetails);
        return goodDetails;
    }


    /**
     * @param data
     * @return
     */
    private static List<PassportDetail> createPassportDetailList(List<String> data) {
        List<PassportDetail> pdList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        for (String s : data) {
            String[] passport = s.trim().split("\\s");
            PassportDetail pd = new PassportDetail();
            for (String p : passport) {
                String[] kv = p.split(":");
                switch (kv[0]) {
                    case "ecl":
                        pd.setEyeColor(kv[1]);
                        break;
                    case "pid":
                        pd.setPassportID(kv[1]);
                        break;
                    case "eyr":
                        pd.setExpirationYear(kv[1]);
                        break;
                    case "byr":
                        pd.setBirthYear(kv[1]);
                        break;
                    case "iyr":
                        pd.setIssueYear(kv[1]);
                        break;
                    case "hgt":
                        pd.setHeight(kv[1]);
                        break;
                    case "hcl":
                        pd.setHairColor(kv[1]);
                        break;
                    case "cid":
                        pd.setCountryID(kv[1]);
                        break;
                    default:
                        System.out.println("no match");
                }
            }
            pdList.add(pd);
        }
        // process the list of pass details
        pdList.stream().forEach(System.out::println);
        return pdList;
    }


    /**
     * process input file
     *
     * @return
     * @throws FileNotFoundException
     */
    List<String> processPassportfile() throws FileNotFoundException {
        String[] valueStr = {};
        try {
            File file = new File("src/com/bart/day4/input.txt");

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);

            fis.read(bytes);

            fis.close();

            valueStr = new String(bytes).trim().split("(?m)^\\s*$");

            //System.out.println("SIZE:" + valueStr.length);
            //System.out.println(Arrays.toString(valueStr));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(valueStr);
    }

}

class PassportDetail {

    // (Birth Year)
    String birthYear;
    // (Issue Year)
    String issueYear;
    // (Expiration Year)
    String expirationYear;
    // (Height)
    String height;
    // (Hair Color)
    String hairColor;
    // (Eye Color)
    String eyeColor;
    // (Passport ID)
    String passportID;
    // (Country ID)
    String countryID;

    /**
     * byr (Birth Year)
     * iyr (Issue Year)
     * eyr (Expiration Year)
     * hgt (Height)
     * hcl (Hair Color)
     * ecl (Eye Color)
     * pid (Passport ID)
     * cid (Country ID) - not required
     *
     * @return
     */
    public boolean validate() {
        if (isBlankString(birthYear) || isBlankString(issueYear) || isBlankString(expirationYear) ||
                isBlankString(height) || isBlankString(hairColor) || isBlankString(eyeColor) ||
                isBlankString(passportID)) {
            return false;
        } else if (!checkRanges()) {
            return false;
        }
        return true;
    }

    /**
     * cid (Country ID) - ignored, missing or not.
     *
     * @return
     */
    private boolean checkRanges() {
        // Birth Year
        // byr (Birth Year) - four digits; at least 1920 and at most 2002.
        int birthLength = birthYear.length();
        if (birthLength == 4) {
            int birthInt = Integer.parseInt(birthYear);
            if (birthInt < 1920 || birthInt > 2002) {
                return false;
            }
        } else {
            return false;
        }

        // Issue Year
        // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        int issueLength = issueYear.length();
        if (issueLength == 4) {
            int issueInt = Integer.parseInt(issueYear);
            if (issueInt < 2010 || issueInt > 2020) {
                return false;
            }
        } else {
            return false;
        }

        // Expiration Year
        // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        int expLength = expirationYear.length();
        if (expLength == 4) {
            int expInt = Integer.parseInt(expirationYear);
            if (expInt < 2020 || expInt > 2030) {
                return false;
            }
        } else {
            return false;
        }

        // Height
        // hgt (Height) - a number followed by either cm or in:
        // If cm, the number must be at least 150 and at most 193.
        // If in, the number must be at least 59 and at most 76.
        char ch = height.charAt(0);
        // start with digit
        if (Character.isDigit(ch)) {
            String[] heightPieces = height.split("(?<=\\d)(?=\\D)");
            // we have two pieces
            if (heightPieces.length == 2) {
                // validate cm
                if (heightPieces[1].equals("cm")) {
                    int heightCm = Integer.parseInt(heightPieces[0]);
                    if (heightCm < 150 || heightCm > 193) {
                        return false;
                    } // validate in
                } else if (heightPieces[1].equals("in")) {
                    int heightIn = Integer.parseInt(heightPieces[0]);
                    if (heightIn < 59 || heightIn > 76) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

        // Hair Color
        // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        char hairCh = hairColor.charAt(0);
        if (hairCh == '#') {
            // should be six characters after the #
            if (hairColor.length() == 7) {
                for (int hI = 1; hI < hairColor.length(); hI++) {
                    // skip first one
                    if ((hairColor.charAt(hI) >= '0' && hairColor.charAt(hI) <= '9') ||
                            (hairColor.charAt(hI) >= 'a' && hairColor.charAt(hI) <= 'f')) {
                        //nop
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

        // Eye Color
        // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        switch (eyeColor) {
            case "amb":
                break;
            case "blu":
                break;
            case "brn":
                break;
            case "gry":
                break;
            case "grn":
                break;
            case "hzl":
                break;
            case "oth":
                break;
            default: {
                return false;
            }
        }

        // Passport ID
        // pid (Passport ID) - a nine-digit number, including leading zeroes.
        if (passportID.length() == 9) {
            try {
                Integer.parseInt(passportID);
            } catch (NumberFormatException ne) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getIssueYear() {
        return issueYear;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public String getHeight() {
        return height;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getPassportID() {
        return passportID;
    }

    public String getCountryID() {
        return countryID;
    }


    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void setIssueYear(String issueYear) {
        this.issueYear = issueYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    @Override public String toString() {
        return "PassportDetail{" +
                "birthYear='" + birthYear + '\'' +
                ", issueYear='" + issueYear + '\'' +
                ", expirationYear='" + expirationYear + '\'' +
                ", height='" + height + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", passportID='" + passportID + '\'' +
                ", countryID='" + countryID + '\'' +
                '}';
    }
}
