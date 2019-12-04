using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using Plugin.Vibrate;
using Xamarin.Essentials;
using Plugin.Toast;

namespace Molby.Views
{
    public partial class LeveltPage : ContentPage
    {
        public LeveltPage()
        {
            InitializeComponent();
        }

        private void BtUnlockClicked(object sender, EventArgs e)
        {
            if (btunlocked.Text != "UNLOCKED")
            {
                Vibration.Vibrate();
                var duration = TimeSpan.FromSeconds(0.5);
                CrossToastPopUp.Current.ShowToastMessage("The new Level " + "Naruto" + " is Unlocked");
                Vibration.Vibrate(duration);
                btunlocked.Text = "UNLOCKED";
                btunlocked.BackgroundColor = Color.YellowGreen;
            }
        }
    }
}
