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
        activity?.title = "MSHookGen"

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

            if(msHookGenAppendMode.isChecked) {
                resolute = "${returnValue} (*_${className}_${classFunction})(${argument});\n"+
                        "${returnValue} ${className}_${classFunction}(${argumentNameConversion(argument)[0]}) {\n"+
                        "  _${className}_${classFunction}(${argumentNameConversion(argument)[1]});\n"+
                        "}"
            } else {
                resolute = "${returnValue} (*_${className}_${classFunction})(${argument});\n"+
                        "${returnValue} ${className}_${classFunction}(${argumentNameConversion(argument)[0]}) {\n"+
                        "  \n"+
                        "}"
            }
        } else {
            resolute = "Error"
        }

        return resolute
    }

    private fun argumentNameConversion(argument : String) : ArrayList<String> {
        var resolute = arrayListOf("","")

        var argList = argument.split(",")
        var first = true
        argList.forEach {
            if(!first) {
                resolute[0] += ", "
                resolute[1] += ", "
            }
            first = false
            if(it.indexOf("*") == -1) {
                val firstChar = it.substring(0, 1)
                val upFirstChar = firstChar.toLowerCase()
                if(firstChar == upFirstChar) {
                    resolute[0] += "$it _$it"
                    resolute[1] += "_$it"
                } else {
                    resolute[0] += "$it ${it.replaceRange(0, 1, upFirstChar)}"
                    resolute[1] += it.replaceRange(0, 1, upFirstChar)
                }
            } else {
                val firstChar = it.substring(0, 1)
                val upFirstChar = firstChar.toLowerCase()
                val removePointer = it.substring(0, it.indexOf("*"))

                if(firstChar == upFirstChar) {
                    resolute[0] += "$it _$removePointer"
                    resolute[1] += "_$removePointer"
                } else {
                    resolute[0] += "$it ${removePointer.replaceRange(0, 1, upFirstChar)}"
                    resolute[1] += removePointer.replaceRange(0, 1, upFirstChar)
                }
            }
        }
        return resolute
    }

    private fun copyToClipboard(text : String) {
        val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText("", text)
        makeSnackBar(view!!,resources.getString(R.string.mshook_gen_resolute_copy_message))
    }
}