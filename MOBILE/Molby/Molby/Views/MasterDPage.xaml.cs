using Newtonsoft.Json;
using System.Net.Http;
using Xamarin.Forms;

namespace Molby.Views
{
    public partial class MasterDPage : MasterDetailPage
    {
        public MasterDPage()
        {
            Detail = new LoginPage();
            InitializeComponent();
            this.IsPresented = false;
            //GetRegistration();
        }
        public async void GetRegistration()
        {
            var httpClient = new HttpClient();
            var response = await httpClient.GetStringAsync("http://localhost:56103/api/Registrations ");
            //var employee = JsonConvert.DeserializeObject<List<employee>>(response);
            //LV.ItemsSource = employee;
        }

        private void BtClickedHome(object sender, System.EventArgs e)
        {
            Detail = new HomePage();
            this.IsPresented = false;
        }

        private void BtClickedSearch(object sender, System.EventArgs e)
        {
            Detail = new GroupPage();
            this.IsPresented = false;
        }

        private void BtClickedRewards(object sender, System.EventArgs e)
        {
            Detail = new RewardPage();
            this.IsPresented = false;
        }

        private void BtClickedProfil(object sender, System.EventArgs e)
        {
            Detail = new LeveltPage();
            this.IsPresented = false;
        }

        private void BtClickedAdding(object sender, System.EventArgs e)
        {
            Detail = new UserReward();
            this.IsPresented = false; ;
        }
    }
};