package com.github.Yimizhao.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;
import org.springframework.util.StringUtils;
/**
 *  规则
 *    +资源字符串+权限位+实例ID
 *
 *  以+开头 中间通过+分割
 *
 *  权限：
 *     0 表示所有权限
 *     1 新增 0001
 *     2 修改 0010
 *     4 删除 0100
 *     8 查看 1000
 *
 *  如 +user+10 表示对资源user拥有修改/查看权限
 *
 *  不考虑一些异常情况
 *
 * <p>User:
 * <p>Date:
 * <p>Version: 1.0
 */
public class BitPermission implements Permission {

    private String resourceIdentify;
    private int permissionBit;
    private String instanceId;

    public BitPermission(String permissionString) {
        // 分割成四组，第一组为空（如：字符串“+资源字符串+权限位+实例ID”分割后为{“”，“资源字符串”，“权限位”，“实例ID”}）
        String[] array = permissionString.split("\\+");

        if (array.length > 1) {
            resourceIdentify = array[1];
        }

        if (!StringUtils.hasText(resourceIdentify)) {
            resourceIdentify = "*";
        }

//        if (!StringUtils.hasText(resourceIdentify = array[1])) {
//            resourceIdentify = "*";
//        }

        if (array.length > 2) {
            permissionBit = Integer.parseInt(array[2]);
        }

        if (array.length > 3) {
            instanceId = array[3];
        }

        if (!StringUtils.hasText(instanceId)) {
            instanceId = "*";
        }

    }

    @Override
    public boolean implies(Permission p) {
        if (!(p instanceof BitPermission)) {
            return false;
        }
        BitPermission other = (BitPermission) p;

        if (!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
            return false;
        }

        if (!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)) {
            return false;
        }

        if (!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BitPermission{" +
                "resourceIdentify='" + resourceIdentify + '\'' +
                ", permissionBit=" + permissionBit +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
