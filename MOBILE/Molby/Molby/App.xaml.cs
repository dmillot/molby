using Prism;
using Prism.Ioc;
using Molby.ViewModels;
using Molby.Views;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using Windows.UI.Notifications;
using Plugin.Toasts;
using Microsoft.VisualStudio.Services.Client;
using System;
using Xamarin.Essentials;
using Plugin.LocalNotifications;

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
            
            //await NavigationService.NavigateAsync(nameof(MainPage) + "/" + nameof(MasterDPage));
            await NavigationService.NavigateAsync("NavigationPage/MasterDPage");
            //await NavigationService.NavigateAsync("NavigationPage/LeveltPage");
            //await NavigationService.NavigateAsync("NavigationPage/LoginPage");

            NotificationComponment();
        }

        public async void NotificationComponment()
        {
            Vibration.Vibrate();
            var duration = TimeSpan.FromSeconds(1);
            Vibration.Vibrate(duration);
            DateTime dateTime = new DateTime();
            CrossLocalNotifications.Current.Show("Molby Application", "All Molby services are started", 1);
            // Code omitted for clarity - here is where the service would do something.

            // Work has finished, now dispatch anotification to let the user know.
            //new Notification.Builder(this).SetContentTitle("Molby").SetContentText("All Molby services are running").Build();

        }

        protected override void RegisterTypes(IContainerRegistry containerRegistry)
        {
            containerRegistry.RegisterForNavigation<NavigationPage>();
            containerRegistry.RegisterForNavigation<MainPage, MainPageViewModel>();
            containerRegistry.RegisterForNavigation<LoginPage, LoginPageViewModel>();
            containerRegistry.RegisterForNavigation<MasterDPage, MasterDPageViewModel>();
            containerRegistry.RegisterForNavigation<LeveltPage, LeveltPageViewModel>();
            containerRegistry.RegisterForNavigation<RewardPage, RewardPageViewModel>();
            containerRegistry.RegisterForNavigation<GroupPointPage, GroupPointPageViewModel>();
            containerRegistry.RegisterForNavigation<GroupPage, GroupPageViewModel>();
            containerRegistry.RegisterForNavigation<UserReward, UserRewardViewModel>();
            containerRegistry.RegisterForNavigation<HomePage, HomePageViewModel>();
        }
    }
}
