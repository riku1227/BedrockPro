package jp.riku1227.bedrockpro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.bedrockpro.R
import kotlinx.android.synthetic.main.fragment_mshook_gen.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import jp.riku1227.bedrockpro.makeSnackBar


class MSHookGenFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mshook_gen, container, false)
    }

    override fun onStart() {
        super.onStart()

        msHookGenGenerate.setOnClickListener {
            val functionName = msHookGenFunctionName.text.toString()
            var returnValue = msHookGenReturnValue.text.toString()

            if(returnValue == "") {
                returnValue = "void"
            }

            msHookGenResoluteFunction.text = createFunction(functionName, returnValue)
            msHookGenResoluteMSHook.text = createMSHook(functionName)
        }

        msHookGenResoluteFunctionCopy.setOnClickListener {
            copyToClipboard(msHookGenResoluteFunction.text.toString())
        }

        msHookGenResoluteMSHookCopy.setOnClickListener {
            copyToClipboard(msHookGenResoluteMSHook.text.toString())
        }
    }

    private fun createMSHook(functionName : String) : String {
        var resolute = ""
        var className = ""
        var classFunction = ""

        if (functionName.indexOf("::") != -1) {
            className = functionName.substring(0, functionName.indexOf("::"))
            classFunction = functionName.substring(functionName.indexOf("::") + 2, functionName.indexOf("("))
            resolute = "MSHookFunction((void*)&${className}::${classFunction},(void*)&${className}_${classFunction},(void**) &_${className}_${classFunction});"
        } else {
            resolute = "Error"
        }

        return resolute
    }

    private fun createFunction(functionName : String, returnValue: String) : String {
        var resolute = ""
        var className = ""
        var classFunction = ""
        var argument = ""

        if (functionName.indexOf("::") != -1) {
            className = functionName.substring(0, functionName.indexOf("::"))
            classFunction = functionName.substring(functionName.indexOf("::") + 2, functionName.indexOf("("))
            argument = functionName.substring(functionName.indexOf("(") + 1, functionName.indexOf(")"))

            if(argument == "" || argument == "void") {
                argument = "$className*"
            } else {
                argument = "$className*,$argument"
            }

            resolute = "${returnValue} (*_${className}_${classFunction})(${argument});\n"+
                    "${returnValue} ${className}_${classFunction}(${argument}) {\n"+
                    "  \n"+
                    "}"
        } else {
            resolute = "Error"
        }

        return resolute
    }

    private fun copyToClipboard(text : String) {
        val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText("", text)
        makeSnackBar(view!!,resources.getString(R.string.mshook_gen_resolute_copy_message))
    }
}