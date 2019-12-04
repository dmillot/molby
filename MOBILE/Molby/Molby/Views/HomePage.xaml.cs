using System;
using Xamarin.Essentials;
using Xamarin.Forms;

namespace Molby.Views
{
    public partial class HomePage : ContentPage
    {
        public HomePage()
        {
            InitializeComponent();
        }
        private void imbLevelImageClicked(object sender, EventArgs e)
        {
            Vibration.Vibrate();
            var duration = TimeSpan.FromSeconds(1);
            Vibration.Vibrate(duration);
            DisplayAlert("Authentification Error", "Your password is denied", "OK");
            NavigationPage navigationPage = new NavigationPage(new LeveltPage());
        }

        private void IBRewardClicked(object sender, EventArgs e)
        {
            Vibration.Vibrate();
            var duration = TimeSpan.FromSeconds(0.2);
            Vibration.Vibrate(duration);
            Navigation.PushAsync(new RewardPage());
        }
    }
}
