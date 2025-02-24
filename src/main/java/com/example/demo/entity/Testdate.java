package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName testDate
 */
@TableName(value ="testDate")
@Data
public class Testdate {
    /**
     * 
     */
    private String c1;

    /**
     * 
     */
    private String c2;

    /**
     * 
     */
    private String c3;

    /**
     * 
     */
    private String c4;

    /**
     * 
     */
    private String c5;

    /**
     * 
     */
    private String c6;

    /**
     * 
     */
    private String c7;

    /**
     * 
     */
    private String c8;

    /**
     * 
     */
    private String c9;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Testdate other = (Testdate) that;
        return (this.getC1() == null ? other.getC1() == null : this.getC1().equals(other.getC1()))
            && (this.getC2() == null ? other.getC2() == null : this.getC2().equals(other.getC2()))
            && (this.getC3() == null ? other.getC3() == null : this.getC3().equals(other.getC3()))
            && (this.getC4() == null ? other.getC4() == null : this.getC4().equals(other.getC4()))
            && (this.getC5() == null ? other.getC5() == null : this.getC5().equals(other.getC5()))
            && (this.getC6() == null ? other.getC6() == null : this.getC6().equals(other.getC6()))
            && (this.getC7() == null ? other.getC7() == null : this.getC7().equals(other.getC7()))
            && (this.getC8() == null ? other.getC8() == null : this.getC8().equals(other.getC8()))
            && (this.getC9() == null ? other.getC9() == null : this.getC9().equals(other.getC9()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getC1() == null) ? 0 : getC1().hashCode());
        result = prime * result + ((getC2() == null) ? 0 : getC2().hashCode());
        result = prime * result + ((getC3() == null) ? 0 : getC3().hashCode());
        result = prime * result + ((getC4() == null) ? 0 : getC4().hashCode());
        result = prime * result + ((getC5() == null) ? 0 : getC5().hashCode());
        result = prime * result + ((getC6() == null) ? 0 : getC6().hashCode());
        result = prime * result + ((getC7() == null) ? 0 : getC7().hashCode());
        result = prime * result + ((getC8() == null) ? 0 : getC8().hashCode());
        result = prime * result + ((getC9() == null) ? 0 : getC9().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", c1=").append(c1);
        sb.append(", c2=").append(c2);
        sb.append(", c3=").append(c3);
        sb.append(", c4=").append(c4);
        sb.append(", c5=").append(c5);
        sb.append(", c6=").append(c6);
        sb.append(", c7=").append(c7);
        sb.append(", c8=").append(c8);
        sb.append(", c9=").append(c9);
        sb.append("]");
        return sb.toString();
    }
}