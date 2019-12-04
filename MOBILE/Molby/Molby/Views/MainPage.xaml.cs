using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using Plugin.Vibrate;
using Xamarin.Essentials;

namespace Molby.Views
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
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