package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEditPoemScreenBinding
import io.github.chhabra_dhiraj.poempre.domain.Sentence
import io.github.chhabra_dhiraj.poempre.viewmodels.AppInsideViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class EditPoemScreenFragment : Fragment(), EditGenreBottomModalSheetFragment.ScreenListener,
    EditTitleModalBottomSheetFragment.TitleListener {

    private val args: EditPoemScreenFragmentArgs by navArgs()

    private var _binding: FragmentEditPoemScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AppInsideViewModel
            by navGraphViewModels(R.id.main_content_navigation_graph)

    private var sentences: List<Sentence>? = null
    private var index: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
// Inflate the layout for this fragment
        _binding = FragmentEditPoemScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!args.plus) {
            binding.titleEditPoemScreenFragment.setText(args.poem!!.title)
            val genre = when (args.poem!!.genre) {
                "love" -> {
                    "Love"
                }
                "nature" -> {
                    "Nature"
                }
                else -> {
                    "Mythology"
                }
            }
            binding.genreEditPoemScreenFragment.setText(genre)
            binding.bodyEditPoemScreenFragment.setText(args.poem!!.body)
        } else {
            val editTitleModalBottomSheet = EditTitleModalBottomSheetFragment()
            editTitleModalBottomSheet.setListener(this)
            editTitleModalBottomSheet.show(
                childFragmentManager,
                "editModal"
            )
        }

        binding.toolbarEditPoemScreenFragment.setOnMenuItemClickListener {
            if (it.itemId == R.id.editTitleBottomModalSheetFragment) {
                val editTitleModalBottomSheet = EditTitleModalBottomSheetFragment()
                editTitleModalBottomSheet.setListener(this)
                editTitleModalBottomSheet.show(
                    childFragmentManager,
                    "titleModal"
                )
            } else if (it.itemId == R.id.editGenreBottomModalSheetFragment) {
                val editGenreBottomModalSheetFragment = EditGenreBottomModalSheetFragment()
                editGenreBottomModalSheetFragment.setListener(this)
                editGenreBottomModalSheetFragment.show(
                    childFragmentManager,
                    "genreModal"
                )
            } else {
                if (!args.plus) {
                    val title = binding.titleEditPoemScreenFragment.text.toString()
                    val genre = binding.genreEditPoemScreenFragment.text.toString()
                    val body = binding.bodyEditPoemScreenFragment.text.toString()
                    // Update Poem call
                    viewModel.updatePoem(args.poem!!.poetryId, title, genre, body)
                } else {
                    val title = binding.titleEditPoemScreenFragment.text.toString()
                    val genre = binding.genreEditPoemScreenFragment.text.toString()
                    val body = binding.bodyEditPoemScreenFragment.text.toString()
                    // Create Poem call
                    viewModel.createPoem(title, genre, body)
                }
            }

            viewModel.isUpdateSuccessful.observe(viewLifecycleOwner, Observer {
                if (it) {
                    findNavController().popBackStack(R.id.homeScreenFragment, false)
                } else {
                    Snackbar.make(binding.root, "Server Error", Snackbar.LENGTH_SHORT).show()
                }
            })

            viewModel.isCreateSuccessful.observe(viewLifecycleOwner, Observer {
                if (it) {
                    findNavController().popBackStack(R.id.homeScreenFragment, false)
                } else {
                    Snackbar.make(binding.root, "Server Error", Snackbar.LENGTH_SHORT).show()
                }
            })

            return@setOnMenuItemClickListener true
        }

        binding.extendedFabSuggestionsEditPoemScreenFragment.setOnClickListener {
            val selectionEnd: Int = binding.bodyEditPoemScreenFragment.selectionEnd
            var text: String = binding.bodyEditPoemScreenFragment.text.toString()
            if (selectionEnd >= 0) {
                // gives you the substring from start to the current cursor
                // position
                text = text.substring(0, selectionEnd)
            }
            val delimiter = " "
            val lastDelimiterPosition = text.lastIndexOf(delimiter)
            val lastWord =
                if (lastDelimiterPosition == -1) text else text.substring(lastDelimiterPosition + delimiter.length)

            val genre: String = when (binding.genreEditPoemScreenFragment.text.toString()) {
                "Love" -> {
                    "love"
                }
                "Nature" -> {
                    "nature"
                }
                else -> {
                    "myth"
                }
            }

            viewModel.getSuggestions(lastWord, genre)

        }

        binding.floatingActionButtonInsertEditPoemScreenFragment.setOnClickListener {
            var body = binding.bodyEditPoemScreenFragment.text.toString()
            body += " " + sentences!![index].sentence
            binding.bodyEditPoemScreenFragment.setText(body)
            binding.bodyEditPoemScreenFragment.setSelection(binding.bodyEditPoemScreenFragment.text.length)
        }

        viewModel.sentences.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    binding.floatingActionButtonInsertEditPoemScreenFragment.visibility =
                        View.VISIBLE
                    binding.linearLayoutSuggestionsEditPoemScreenFragment.visibility = View.VISIBLE
                    sentences = it
                    binding.tvSuggestionsEditPoemScreenFragment.text = sentences!![0].sentence
                    index = 0
                } else {
                    Snackbar.make(binding.root, "No similar sentences found", Snackbar.LENGTH_SHORT)
                        .show()
                }
            } else {
                Snackbar.make(binding.root, "Server Error", Snackbar.LENGTH_SHORT)
                    .show()
            }
        })

        if (sentences != null) {
            adjustButtonVisibility()
        }

        binding.floatingActionButtonRightEditPoemScreenFragment.setOnClickListener {
            index++
            binding.tvSuggestionsEditPoemScreenFragment.text = sentences!![index].sentence
            adjustButtonVisibility()
        }

        binding.floatingActionButtonRightEditPoemScreenFragment.setOnClickListener {
            index--
            binding.tvSuggestionsEditPoemScreenFragment.text = sentences!![index].sentence
            adjustButtonVisibility()
        }
    }

    private fun adjustButtonVisibility() {
        if (index == 0) {
            binding.floatingActionButtonLeftEditPoemScreenFragment.visibility = View.GONE
        } else {
            binding.floatingActionButtonLeftEditPoemScreenFragment.visibility = View.VISIBLE
        }

        if (index == (sentences!!.size - 1)) {
            binding.floatingActionButtonRightEditPoemScreenFragment.visibility = View.GONE
        } else {
            binding.floatingActionButtonRightEditPoemScreenFragment.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun done(genre: String) {
        val shownGenre = when (genre) {
            "love" -> {
                "Love"
            }
            "nature" -> {
                "Nature"
            }
            else -> {
                "Mythology"
            }
        }
        binding.genreEditPoemScreenFragment.text = shownGenre
    }

    override fun doneTitle(title: String) {
        binding.titleEditPoemScreenFragment.text = title
    }

}
