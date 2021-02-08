package com.mercadolibre.android.andesui.demoapp.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.feature.specs.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.feature.specs.launchSpecs
import com.mercadolibre.android.andesui.demoapp.feature.utils.PageIndicator
import com.mercadolibre.android.andesui.message.AndesMessage
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import com.mercadolibre.android.andesui.textfield.AndesTextarea
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

class MessageShowcaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)

        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.title = resources.getString(R.string.andesui_demoapp_screen_message)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewPager = findViewById<ViewPager>(R.id.andesui_viewpager)
        viewPager.adapter = AndesShowcasePagerAdapter(this)
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    class AndesShowcasePagerAdapter(private val context: Context) : PagerAdapter() {

        var views: List<View>

        init {
            views = initViews()
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            container.addView(views[position])
            return views[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View?)
        }

        override fun isViewFromObject(view: View, other: Any): Boolean {
            return view == other
        }

        override fun getCount(): Int = views.size

        private fun initViews(): List<View> {
            val inflater = LayoutInflater.from(context)

            val staticMessagesLayout = addStaticMessages(inflater)
            val dynamicMessageLayout = addDynamicMessage(inflater)

            return listOf(dynamicMessageLayout, staticMessagesLayout)
        }

        @Suppress("MagicNumber", "LongMethod")
        private fun addDynamicMessage(inflater: LayoutInflater): View {
            val layoutMessagesChange = inflater.inflate(
                R.layout.andesui_message_showcase_change,
                null,
                false
            ) as ScrollView

            val hierarchySpinner: Spinner = layoutMessagesChange.findViewById(R.id.hierarchy_spinner)
            ArrayAdapter.createFromResource(
                context,
                R.array.hierarchy_spinner,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                hierarchySpinner.adapter = adapter
            }

            val typeSpinner: Spinner = layoutMessagesChange.findViewById(R.id.simple_type_spinner)
            ArrayAdapter.createFromResource(
                context,
                R.array.state_spinner,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeSpinner.adapter = adapter
            }

            val thumbnailSpinner: Spinner = layoutMessagesChange.findViewById(R.id.thumbnail_spinner)
            ArrayAdapter.createFromResource(
                context,
                R.array.thumbnail_spinner,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                thumbnailSpinner.adapter = adapter
            }

            val dismissableCheckbox = layoutMessagesChange.findViewById<AndesCheckbox>(R.id.dismissable_checkbox)

            val bodyText = layoutMessagesChange.findViewById<AndesTextarea>(R.id.body_text)

            val titleText = layoutMessagesChange.findViewById<AndesTextfield>(R.id.title_text)

            val primaryActionText = layoutMessagesChange.findViewById<AndesTextfield>(R.id.primary_action_text)

            val secondaryActionText = layoutMessagesChange.findViewById<AndesTextfield>(R.id.secondary_action_text)

            val linkActionText = layoutMessagesChange.findViewById<AndesTextfield>(R.id.link_action_text)

            val changeButton = layoutMessagesChange.findViewById<AndesButton>(R.id.change_button)
            val changeMessage = layoutMessagesChange.findViewById<AndesMessage>(R.id.message)

            val links = listOf(
                AndesBodyLink(4, 11),
                AndesBodyLink(60, 66),
                AndesBodyLink(79, 122),
                AndesBodyLink(50, 40)
            )

            changeMessage.bodyLinks = (AndesBodyLinks(
                links,
                listener = {
                    Toast.makeText(context, "Click at body link: $it", Toast.LENGTH_SHORT).show()
                }
            ))

            changeButton.setOnClickListener {
                if (bodyText.text.toString().isEmpty()) {
                    bodyText.state = AndesTextfieldState.ERROR
                    bodyText.helper = "Message cannot be visualized with null body"
                    bodyText.requestFocus()
                    return@setOnClickListener
                } else {
                    bodyText.state = AndesTextfieldState.IDLE
                    bodyText.helper = null
                    changeMessage.body = bodyText.text.toString()
                }

                changeMessage.isDismissable = dismissableCheckbox.status == AndesCheckboxStatus.SELECTED
                changeMessage.title = titleText.text.toString()
                changeMessage.type = AndesMessageType.fromString(typeSpinner.selectedItem.toString())
                changeMessage.hierarchy = AndesMessageHierarchy.fromString(hierarchySpinner.selectedItem.toString())
                changeMessage.bodyLinks = null

                if (primaryActionText.text.toString().isNotEmpty()) {
                    changeMessage.setupPrimaryAction(
                        primaryActionText.text.toString(),
                        View.OnClickListener {
                            Toast.makeText(context, "Primary onClick", Toast.LENGTH_SHORT).show()
                        }
                    )
                    changeMessage.hideLinkAction()
                } else {
                    changeMessage.hidePrimaryAction()
                }

                if (dismissableCheckbox.status == AndesCheckboxStatus.SELECTED) {
                    changeMessage.setupDismissableCallback(
                        View.OnClickListener {
                            Toast.makeText(context, "Dismiss onClick", Toast.LENGTH_LONG).show()
                        }
                    )
                }

                if (secondaryActionText.text.toString().isNotEmpty()) {
                    when {
                        primaryActionText.text.toString() != "" -> {
                            changeMessage.setupSecondaryAction(
                                secondaryActionText.text.toString(),
                                View.OnClickListener {
                                            Toast.makeText(context, "Secondary onClick", Toast.LENGTH_SHORT).show()
                                        }
                            )
                        }
                        else -> {
                            Toast.makeText(
                                context,
                                "Cannot set a secondary action without a primary one",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    changeMessage.hideSecondaryAction()
                }

                if (linkActionText.text.toString().isNotEmpty()) {
                    when {
                        primaryActionText.text.toString() == "" -> {
                            changeMessage.setupLinkAction(
                                linkActionText.text.toString(),
                                View.OnClickListener {
                                    Toast.makeText(context, "link onClick", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                        else -> {
                            Toast.makeText(
                                context,
                                "Cannot set a link action with a primary one",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    changeMessage.hideLinkAction()
                }

                val thumbnailDrawable = if(thumbnailSpinner.selectedItem.toString() == "With Thumbnail") {
                    ResourcesCompat.getDrawable(context.resources, R.mipmap.andesui_demoapp_ic_launcher, null)
                } else {
                    null
                }

                changeMessage.setupThumbnail(thumbnailDrawable)

                changeMessage.visibility = View.VISIBLE
            }

            return layoutMessagesChange
        }

        private fun addStaticMessages(inflater: LayoutInflater): View {
            val layoutMessages = inflater.inflate(
                R.layout.andesui_message_showcase,
                null,
                false
            ) as ScrollView

            layoutMessages.findViewById<AndesButton>(R.id.andesui_demoapp_andes_specs_message).setOnClickListener {
                launchSpecs(it.context, AndesSpecs.MESSAGE)
            }

            layoutMessages.findViewById<AndesMessage>(R.id.messagePrimaryAction)
                    .setupPrimaryAction(
                        "Primary",
                        View.OnClickListener {
                            Toast.makeText(context, "Primary onClick", Toast.LENGTH_SHORT).show()
                        }
                    )

            layoutMessages.findViewById<AndesMessage>(R.id.messagePrimaryAndSecondaryActionQuiet)
                    .setupPrimaryAction(
                        "Primary",
                        View.OnClickListener {
                            Toast.makeText(context, "Primary onClick", Toast.LENGTH_SHORT).show()
                        }
                    )
            layoutMessages.findViewById<AndesMessage>(R.id.messagePrimaryAndSecondaryActionQuiet)
                    .setupSecondaryAction(
                        "Secondary",
                        View.OnClickListener {
                            Toast.makeText(context, "Secondary onClick", Toast.LENGTH_SHORT).show()
                        }
                    )

            layoutMessages.findViewById<AndesMessage>(R.id.messagePrimaryAndSecondaryActionLoud)
                    .setupPrimaryAction(
                        "Primary",
                        View.OnClickListener {
                            Toast.makeText(context, "Primary onClick", Toast.LENGTH_SHORT).show()
                        }
                    )
            layoutMessages.findViewById<AndesMessage>(R.id.messagePrimaryAndSecondaryActionLoud)
                    .setupSecondaryAction(
                        "Secondary",
                        View.OnClickListener {
                            Toast.makeText(context, "Secondary onClick", Toast.LENGTH_SHORT).show()
                        }
                    )

            layoutMessages.findViewById<AndesMessage>(R.id.messageLinkLoud)
                    .setupLinkAction(
                        "Link",
                        View.OnClickListener {
                            Toast.makeText(context, "Link onClick", Toast.LENGTH_SHORT).show()
                        }
                    )
            layoutMessages.findViewById<AndesMessage>(R.id.messageLinkQuiet)
                    .setupLinkAction(
                        "Link",
                        View.OnClickListener {
                            Toast.makeText(context, "Link onClick", Toast.LENGTH_SHORT).show()
                        }
                    )
            val bodyLink = 11
            val links = listOf(
                AndesBodyLink(6, bodyLink),
                AndesBodyLink(64, 71)
            )
            layoutMessages.findViewById<AndesMessage>(R.id.messageLinkBody).bodyLinks = (AndesBodyLinks(
                links,
                listener = {
                    Toast.makeText(context, "Click at body link: $it", Toast.LENGTH_SHORT).show()
                }
            ))
            layoutMessages.findViewById<AndesMessage>(R.id.messageLinkBodyWithThumbnail).bodyLinks = (AndesBodyLinks(
                links,
                listener = {
                    Toast.makeText(context, "Click at body link: $it", Toast.LENGTH_SHORT).show()
                }
            ))
            return layoutMessages
        }
    }
}
