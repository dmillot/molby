 using Prism;
using Prism.Ioc;
using Molby.ViewModels;
using Molby.Views;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using Molby.Services;
using Molby.Services.Interfaces;

[assembly: XamlCompilation(XamlCompilationOptions.Compile)]
namespace Molby
{
    public partial class App
    {
        /* 
         * The Xamarin Forms XAML Previewer in Visual Studio uses System.Activator.CreateInstance.
         * This imposes a limitation in which the App class must have a default constructor. 
         * App(IPlatformInitializer initializer = null) cannot be handled by the Activator.
         */
        public App() : this(null) { }

        public App(IPlatformInitializer initializer) : base(initializer) { }

        protected override async void OnInitialized()
        {
            InitializeComponent();

            await NavigationService.NavigateAsync("NavigationPage/TabPage");
        }

        protected override void RegisterTypes(IContainerRegistry containerRegistry)
        {
            containerRegistry.RegisterSingleton<IUserService, UserService>();
            containerRegistry.RegisterSingleton<IGroupService, GroupService>();
            containerRegistry.RegisterSingleton<ILevelService, LevelService>();
            containerRegistry.RegisterSingleton<IRewardService, RewardService>();

            containerRegistry.RegisterForNavigation<NavigationPage>();
            containerRegistry.RegisterForNavigation<GroupPage, GroupPageViewModel>();
            containerRegistry.RegisterForNavigation<LevelsPage, LevelsPageViewModel>();
            containerRegistry.RegisterForNavigation<TabPage, TabPageViewModel>();
            containerRegistry.RegisterForNavigation<ProfilePage, ProfilePageViewModel>();
            containerRegistry.RegisterForNavigation<LevelDescriptionPage, LevelDescriptionPageViewModel>();
            containerRegistry.RegisterForNavigation<RewardsPage, RewardsPageViewModel>();
        }
    }
}
