package com.example.demo.Security;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class AesRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

    private final Map<String, String[]> modifiableParameters;
    private Map<String, String[]> allParameters = null;

    public AesRequestWrapper(HttpServletRequest request,
                             final Map<String, String[]> additionalParams) {
        super(request);
        modifiableParameters = new TreeMap<String, String[]>();
        modifiableParameters.putAll(additionalParams);
        if(modifiableParameters.size() != 0
                && modifiableParameters.containsKey("password")
                && request.getRequestURI().equals("/oauth/token")) {
            String decryptedPass = modifiableParameters.get("password")[0];

            String encryptedPass = decrypt(decryptedPass);

            modifiableParameters.put("password", new String[]{encryptedPass});
        }
    }

    private String decrypt(String enterPass) {
        String decryptedPass = new String(Base64.getDecoder().decode(enterPass));
        AesUtil aesUtil = new AesUtil(128, 1000);
        if(decryptedPass != null && decryptedPass.split("::").length == 3)
        {
            String pass = aesUtil.decrypt(decryptedPass.split("::")[1],
                    decryptedPass.split("::")[0],
                    "privateKey123456",
                    decryptedPass.split("::")[2]);
            return pass;
        } else {
            return null;
        }
    }

    @Override
    public String getParameter(final String name)
    {
        String[] strings = getParameterMap().get(name);
        if (strings != null)
        {
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap()
    {
        if (allParameters == null)
        {
            allParameters = new TreeMap<String, String[]>();
            allParameters.putAll(super.getParameterMap());
            allParameters.putAll(modifiableParameters);
        }
        //Return an unmodifiable collection because we need to uphold the interface contract.
        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames()
    {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name)
    {
        return getParameterMap().get(name);
    }

}
