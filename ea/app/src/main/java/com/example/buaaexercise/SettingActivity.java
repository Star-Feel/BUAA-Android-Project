package com.example.buaaexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SettingActivity extends AppCompatActivity {
    private final String ABOUT_DEVELOPER_CONTENT = "        本作品由北航计算机学院2021级的5名本科生（Goths、夜雨欲止、Vanthon、zbh、@:@:@）于2023年共同开发完成。如果您对本app有疑问或建议， 欢迎通过邮箱（21371455@buaa.edu.cn）联系我们~";
    private final String ABOUT_DEVELOPER_TITLE = "关于开发者";
    private final String KNOWN = "我知道了";
    private final String RUN_RISK_TITLE = "运动风险须知";
    private final String RUN_RISK_CONTENT = "尊敬的用户：\n" +
            "   在参与任何形式的运动前，请注意以下运动风险须知。如果您符合以下条件之一，强烈建议在开始新的运动计划之前咨询医生，确保您的身体状况适合进行相应的运动: \n" +
            "    (1).  存在慢性健康问题： 如果您患有慢性疾病，如心脏病、高血压、糖尿病等，请在开始新的运动计划之前咨询医生。\n" +
            "    (2).  曾经进行手术： 如果您最近进行过手术，尤其是与心脏、关节或骨骼有关的手术，请在开始运动前咨询医生。\n" +
            "    (3).  存在呼吸问题： 如果您有呼吸系统的问题，如哮喘、肺部疾病等，请在运动前咨询医生。\n" +
            "    (4).  年龄较大： 年龄较大的人可能需要更多的健康评估，确保他们的身体状况适合进行一定强度的运动。\n" +
            "    (5).  怀孕： 孕妇在进行运动前应咨询医生，以确保选择的运动对胎儿和母亲都是安全的。\n" +
            "    (6).  骨折或关节问题： 如果您曾经经历过骨折、关节问题或手术，请在进行高强度或影响关节的运动前咨询医生。\n" +
            "    (7).  其他健康状况： 如果您有其他任何可能影响运动安全的健康状况，建议在开始运动前咨询医生。\n" +
            "   在进行运动时，请始终根据您的身体状况调整运动强度，并在出现任何不适或异常症状时及时停止运动并寻求医疗建议。保持健康的体魄，享受运动的乐趣！\n" +
            "   祝您健康愉快！";
    private final String PRIVATE_POLICY_TITLE = "隐私政策";
    private final String PRIVATE_POLICY = "  本应用（以下简称“应用”）尊重并保护所有使用者的隐私。为了更好地保障您的隐私权益，特此向您说明本应用的隐私政策如下：\n" +
            "    (1).  信息收集与使用\n" +
            "        我们可能会收集您提供的个人信息，用于实现应用的正常运行和提供相关服务。\n" +
            "        我们可能会使用您的个人信息进行内部分析，以改善和优化应用的功能和服务。\n" +
            "    (2).  信息保护\n" +
            "        我们采取一系列合理的安全措施，保护您的个人信息免遭未经授权的访问、使用或泄露。\n" +
            "    (3).  信息共享\n" +
            "        除非经过您的明确同意或法律规定，我们不会将您的个人信息分享给第三方。\n" +
            "    (4).  Cookie 和类似技术\n" +
            "        我们可能使用Cookie和类似技术来提高用户体验，并进行网站流量分析。\n" +
            "    (5).  隐私政策的变更\n" +
            "        我们可能会根据法律法规或业务运营需要进行隐私政策的变更，变更后的政策将在应用中进行公示。\n" +
            "    (6).  联系我们\n" +
            "        如果您对隐私政策有任何疑问或意见，可通过应用中提供的联系方式（邮箱：21371455@buaa.edu.cn）与我们联系。";
    private final String PRIVATE_POLICY_ABSTRACT_TITLE = "隐私政策摘要";
    private final String PRIVATE_POLICY_ABSTRACT = "    我们承诺保护用户隐私，收集的个人信息仅用于提供服务和优化用户体验。我们绝不会将您的信息分享给第三方。我们采取安全措施，保障您的个人信息安全。如有隐私政策变更，我们将在应用中及时公示。如有疑问，请随时联系我们。";
    private final String PERSONAL_INFO_COLLECT_LIST_TITLE = "个人信息收集清单";
    private final String PERSONAL_INFO_COLLECT_LIST = "     本应用（以下简称“应用”）可能会收集以下个人信息，用于提供服务和优化用户体验：\n" +
            "    (1). 用户基本信息\n" +
            "        昵称、头像、性别、生日。\n" +
            "    (2). 运动数据\n" +
            "        用户在使用应用进行运动记录时产生的运动信息，包括但不限于运动时长、运动目标等。\n" +
            "       以上个人信息的收集旨在提供更好的服务和优化用户体验，我们承诺不会非法收集、使用或泄露用户个人信息。如有疑问，请随时联系我们（21371455@buaa.edu.cn）。";

    private ImageView returnButton;
    private RelativeLayout goToPersonalInfo;
    private RelativeLayout exitLoginButton;
    private RelativeLayout aboutDeveloperButton;
    private RelativeLayout runRiskButton;
    private RelativeLayout privatePolicyAbstractButton;
    private TextView privatePolicyButton;
    private RelativeLayout personalInfoCollectList;
    private RelativeLayout accountSafeButton;

    public void initAttribute() {
        returnButton = findViewById(R.id.return_button);
        goToPersonalInfo = findViewById(R.id.card_personal_info);
        exitLoginButton = findViewById(R.id.relative_exit);
        aboutDeveloperButton = findViewById(R.id.card_about_developers);
        runRiskButton = findViewById(R.id.card_need_to_know_run_danger);
        privatePolicyAbstractButton = findViewById(R.id.card_private_abstract);
        privatePolicyButton = findViewById(R.id.private_policy_text_view);
        personalInfoCollectList = findViewById(R.id.card_info_collect_list);
        accountSafeButton = findViewById(R.id.card_zhanghao_safe);
    }

    public void setOnClickListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myFrameTitle = "我的";

                Intent intent = new Intent(SettingActivity.this, HomepageActivity.class);
                // 在intent中存一个键值对, 就相当于是传递给跳转的目标页面的参数
                intent.putExtra("fragmentToShow", "我的");
                startActivity(intent);
            }
        });

        goToPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, PersonalInfoActivity.class);
                intent.putExtra("src", "settingActivity");
                startActivity(intent);
            }
        });

        exitLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        aboutDeveloperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹出一个弹窗来显示《关于开发者》的内容
                Tools.showOneButtonDialog(
                        ABOUT_DEVELOPER_TITLE, ABOUT_DEVELOPER_CONTENT, SettingActivity.this, KNOWN);
            }
        });

        runRiskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.showOneButtonDialog(
                        RUN_RISK_TITLE, RUN_RISK_CONTENT, SettingActivity.this, KNOWN);
            }
        });

        privatePolicyAbstractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.showOneButtonDialog(
                    PRIVATE_POLICY_ABSTRACT_TITLE, PRIVATE_POLICY_ABSTRACT, SettingActivity.this, KNOWN);
            }
        });

        privatePolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.showOneButtonDialog(
                        PRIVATE_POLICY_TITLE, PRIVATE_POLICY, SettingActivity.this, KNOWN);
            }
        });

        personalInfoCollectList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.showOneButtonDialog(
                        PERSONAL_INFO_COLLECT_LIST_TITLE, PERSONAL_INFO_COLLECT_LIST, SettingActivity.this, KNOWN);
            }
        });

        accountSafeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 转到账号与安全的活动, 有个注销账号有个修改密码
                Intent intent = new Intent(SettingActivity.this, AccountSafeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initAttribute();
        setOnClickListeners();
    }
}
