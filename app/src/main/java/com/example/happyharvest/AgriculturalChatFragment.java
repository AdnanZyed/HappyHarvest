package com.example.happyharvest;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgriculturalChatFragment extends Fragment {

    private static final String TAG = "AgriculturalChat";

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;
    private EditText messageEditText;
    private Button sendButton;
    private LinearLayout quickQuestionsContainer;

    private final String[] quickQuestions = {
            "كيف أعالج آفات النباتات؟",
            "ما هي أفضل أنواع الأسمدة؟",
            "كيف أزيد إنتاجية المحصول؟",
            "ما هي طرق الري الموفرة للماء؟",
            "كيف أتعامل مع التربة الملحية؟"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agricultural_chat, container, false);


        try {
            initializeViews(view);
            setupChatRecyclerView();
            addWelcomeMessage();
            setupQuickQuestions();
            setupClickListeners();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreateView: " + e.getMessage());
            Toast.makeText(requireContext(), "حدث خطأ في تهيئة الواجهة", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void initializeViews(View view) {
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        messageEditText = view.findViewById(R.id.messageEditText);
        sendButton = view.findViewById(R.id.sendButton);
        quickQuestionsContainer = view.findViewById(R.id.quickQuestionsContainer);

        if (chatRecyclerView == null || messageEditText == null ||
                sendButton == null || quickQuestionsContainer == null) {
            throw new IllegalStateException("One or more views not found in layout");
        }
    }

    private void setupChatRecyclerView() {
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void addWelcomeMessage() {
        addBotMessage("مرحبًا! أنا المساعد الزراعي. كيف يمكنني مساعدتك اليوم؟");
    }

    private void setupQuickQuestions() {
        for (String question : quickQuestions) {
            Button button = new Button(requireContext());
            button.setText(question);
            button.setBackgroundResource(R.drawable.bubble_bot);
            button.setPadding(16, 8, 16, 8);
            button.setTextColor(getResources().getColor(R.color.black));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 8, 0);
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                messageEditText.setText(question);
                sendMessage();
            });

            quickQuestionsContainer.addView(button);
        }
    }

    private void setupClickListeners() {
        sendButton.setOnClickListener(v -> sendMessage());

        messageEditText.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            addUserMessage(message);
            messageEditText.setText("");
            simulateBotResponse(message);
        }
    }

    private void addUserMessage(String message) {
        addMessage(message, true);
    }

    private void addBotMessage(String message) {
        addMessage(message, false);
    }

    private void addMessage(String message, boolean isUser) {
        if (!isAdded() || getActivity() == null) return;

        getActivity().runOnUiThread(() -> {
            ChatMessage chatMessage = new ChatMessage(message, new Date(), isUser);
            chatMessages.add(chatMessage);
            chatAdapter.notifyItemInserted(chatMessages.size() - 1);
            chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
        });
    }

    private void simulateBotResponse(String userMessage) {
        new Handler().postDelayed(() -> {
            String response = generateBotResponse(userMessage);
            addBotMessage(response);
        }, 1500);
    }

    private String generateBotResponse(String userMessage) {
        if (userMessage.contains("آفات") || userMessage.contains("حشرات")) {
            return "لعلاج آفات النباتات:\n1. المبيدات العضوية مثل النيم\n2. المكافحة البيولوجية\n3. الرش بالماء والصابون الطبيعي";
        } else if (userMessage.contains("سماد") || userMessage.contains("أسمدة")) {
            return "أفضل أنواع الأسمدة:\n1. للأشجار: NPK متوازن\n2. للخضروات: غني بالنيتروجين\n3. السماد العضوي لتحسين التربة";
        } else if (userMessage.contains("إنتاجية") || userMessage.contains("محصول")) {
            return "لزيادة الإنتاجية:\n1. أصناف عالية الإنتاج\n2. مواعيد زراعة مناسبة\n3. ري حديث\n4. مراقبة الآفات";
        } else {
            return "شكرًا لسؤالك. سأبحث عن أفضل حل لمشكلتك الزراعية وأعود إليك بالإجابة.";
        }
    }

    private static class ChatMessage {
        final String message;
        final Date date;
        final boolean isUser;

        ChatMessage(String message, Date date, boolean isUser) {
            this.message = message;
            this.date = date;
            this.isUser = isUser;
        }
    }

    private static class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_USER = 1;
        private static final int VIEW_TYPE_BOT = 2;

        private final List<ChatMessage> messages;

        ChatAdapter(List<ChatMessage> messages) {
            this.messages = messages != null ? messages : new ArrayList<>();
        }

        @Override
        public int getItemViewType(int position) {
            if (position < 0 || position >= messages.size()) {
                return VIEW_TYPE_BOT;
            }
            return messages.get(position).isUser ? VIEW_TYPE_USER : VIEW_TYPE_BOT;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            try {
                if (viewType == VIEW_TYPE_USER) {
                    View view = inflater.inflate(R.layout.item_message_user, parent, false);
                    return new UserMessageViewHolder(view);
                } else {
                    View view = inflater.inflate(R.layout.item_message_bot, parent, false);
                    return new BotMessageViewHolder(view);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error inflating layout: " + e.getMessage());
                View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                return new SimpleMessageViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            try {
                if (position < 0 || position >= messages.size()) return;

                ChatMessage message = messages.get(position);
                if (message == null) return;

                String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(message.date);

                if (holder instanceof UserMessageViewHolder) {
                    ((UserMessageViewHolder) holder).bind(message.message, time);
                } else if (holder instanceof BotMessageViewHolder) {
                    ((BotMessageViewHolder) holder).bind(message.message, time);
                } else if (holder instanceof SimpleMessageViewHolder) {
                    ((SimpleMessageViewHolder) holder).bind(message.message);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in onBindViewHolder: " + e.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        static class UserMessageViewHolder extends RecyclerView.ViewHolder {
            private final TextView messageText;
            private final TextView messageTime;

            UserMessageViewHolder(View itemView) {
                super(itemView);
                messageText = itemView.findViewById(R.id.messageText);
                messageTime = itemView.findViewById(R.id.messageTime);

                if (messageText == null || messageTime == null) {
                    throw new IllegalStateException("Missing required views in UserMessageViewHolder");
                }
            }

            void bind(String message, String time) {
                messageText.setText(message);
                messageTime.setText(time);
            }
        }

        static class BotMessageViewHolder extends RecyclerView.ViewHolder {
            private final TextView messageText;
            private final TextView messageTime;

            BotMessageViewHolder(View itemView) {
                super(itemView);
                messageText = itemView.findViewById(R.id.messageText);
                messageTime = itemView.findViewById(R.id.messageTime);

                if (messageText == null || messageTime == null) {
                    throw new IllegalStateException("Missing required views in BotMessageViewHolder");
                }
            }

            void bind(String message, String time) {
                messageText.setText(message);
                messageTime.setText(time);
            }
        }

        static class SimpleMessageViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;

            SimpleMessageViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }

            void bind(String message) {
                textView.setText(message);
            }
        }
    }
}