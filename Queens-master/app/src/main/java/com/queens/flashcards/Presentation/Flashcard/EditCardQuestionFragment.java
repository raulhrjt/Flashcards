package com.queens.flashcards.Presentation.Flashcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.queens.flashcards.Logic.ImageService;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardDataListener;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

/**
 * Class the for Question tab of the Flashcard editing interface.
 */
public class EditCardQuestionFragment extends Fragment {

    //region Members

    private EditText question;
    private Flashcard flashcard;
    private UpdateFlashcardDataListener updateFlashcardDataListener;
    private DrawingView drawingView;
    private ImageView redColorView;
    private ImageView greenColorView;
    private ImageView blackColorView;
    private ImageView blueColorView;
    private ImageView whiteColorView;
    private ImageView purpleColorView;
    private ImageView greyColorView;
    private ImageView orangeColorView;
    private ImageButton incStrokeSize;
    private ImageButton decStrokeSize;
    private ConstraintLayout drawingImageLayout;
    private RadioGroup drawImageGroup;
    private Button saveImage;
    private RadioButton includeImage;

    //endregion

    //region Static Factory Methods

    /**
     * Initializes a new instance of an EditCardQuestionFragment with the specified
     * Flashcard as an argument.
     * @param flashcard The Flashcard to add as an argument.
     * @return A new instance of an EditCardQuestionFragment.
     */
    public static EditCardQuestionFragment newInstance(Flashcard flashcard) {
        EditCardQuestionFragment fragment = new EditCardQuestionFragment();

        if(flashcard != null) {
            Bundle args = new Bundle();
            FlashcardParcelable flashcardParcelable = new FlashcardParcelable(flashcard);
            args.putParcelable(QueensConstants.CARD_KEY, flashcardParcelable);
            fragment.setArguments(args);
        }

        return fragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_card_question, container, false);

        // Get view reference
        question = (EditText) view.findViewById(R.id.et_edit_card_text_question);
        drawingView = (DrawingView) view.findViewById(R.id.dv_image_draw);
        redColorView = (ImageView) view.findViewById(R.id.iv_red_selection);
        greenColorView = (ImageView) view.findViewById(R.id.iv_green_selection);
        blackColorView = (ImageView) view.findViewById(R.id.iv_black_selection);
        blueColorView = (ImageView) view.findViewById(R.id.iv_blue_selection);
        whiteColorView = (ImageView) view.findViewById(R.id.iv_white_selection);
        purpleColorView = (ImageView) view.findViewById(R.id.iv_purple_selection);
        greyColorView = (ImageView) view.findViewById(R.id.iv_grey_selection);
        orangeColorView = (ImageView) view.findViewById(R.id.iv_orange_selection);
        incStrokeSize = (ImageButton) view.findViewById(R.id.ib_increase_stroke_size);
        decStrokeSize = (ImageButton) view.findViewById(R.id.ib_decrease_stroke_size);
        drawingImageLayout = (ConstraintLayout) view.findViewById(R.id.cl_drawing_image_layout);
        drawImageGroup = (RadioGroup) view.findViewById(R.id.rg_include_drawable_image);
        saveImage = (Button) view.findViewById(R.id.btn_save_image);
        includeImage = (RadioButton) view.findViewById(R.id.rb_include_image);

        // Retrieve arguments
        Bundle args = getArguments();
        if(args != null) {
            FlashcardParcelable flashcardParcelable = args.getParcelable(QueensConstants.CARD_KEY);
            flashcard = flashcardParcelable.getFlashcard();
            question.setText(flashcard.getQuestion());

            if(!flashcard.getImageLocation().equals("")) {
                drawingImageLayout.setVisibility(View.VISIBLE);
                includeImage.setChecked(true);
                ImageService imageService = new ImageService(Services.getImagePersistence());
                byte[] imageAsBytes = imageService.getImage(flashcard.getImageLocation());
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

                if(bitmap != null) {
                    drawingView.setBitmap(bitmap);
                }
            }
        }

        // Add listener to update Flashcard
        question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String questionText = editable.toString();
                flashcard = updateFlashcardDataListener.updateFlashcardQuestion(questionText);
            }
        });

        redColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(Color.RED);
            }
        });

        greenColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(ContextCompat.getColor(getContext(), R.color.green));
            }
        });

        blackColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(Color.BLACK);
            }
        });

        blueColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(Color.BLUE);
            }
        });

        whiteColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(Color.WHITE);
            }
        });

        purpleColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(R.color.purple);
            }
        });

        greyColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(ContextCompat.getColor(getContext(), R.color.grey));
            }
        });

        orangeColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.switchColorTo(ContextCompat.getColor(getContext(), R.color.orange));
            }
        });

        incStrokeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.increaseStrokeWidth();
            }
        });

        decStrokeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.decreaseStrokeWidth();
            }
        });

        drawImageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                switch(checkedID) {
                    case R.id.rb_include_image:
                        drawingImageLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_dont_include_image:
                        drawingImageLayout.setVisibility(View.GONE);
                        break;
                }
            }
        });

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = Bitmap.createBitmap(drawingView.getWidth(), drawingView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawingView.draw(canvas);

                updateFlashcardDataListener.updateFlashcardDrawableImage(bitmap);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateFlashcardDataListener = (UpdateFlashcardDataListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString() + " must implement UpdateFlashcardDataListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        question.clearFocus();
    }

    //endregion

}
