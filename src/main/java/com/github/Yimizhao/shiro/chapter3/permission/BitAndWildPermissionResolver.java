package com.github.Yimizhao.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class BitAndWildPermissionResolver implements PermissionResolver {
    /**
     * Returns a new {@link WildcardPermission WildcardPermission} instance constructed based on the specified
     * <tt>permissionString</tt>.
     *
     * @param permissionString the permission string to convert to a {@link Permission Permission} instance.
     * @return a new {@link WildcardPermission WildcardPermission} instance constructed based on the specified
     * <tt>permissionString</tt>
     */
    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }

}
