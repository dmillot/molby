using System.Collections.Generic;
using Xamarin.Forms;

namespace Molby.Views
{
    public partial class UserReward : ContentPage
    {
        public List<GroupProject> GroupProjects { get => _groupProjects; set => _groupProjects = value; }
        private List<GroupProject> _groupProjects;
        public UserReward()
        {
            InitializeComponent();
        }
        public async void UserRwardSelected(object sender, ItemTappedEventArgs e)
        {
            GroupProjects = new List<GroupProject>()
            {
                new GroupProject{ ProjectName = "PROJECT ONE", ProjectNamePoints ="100"},
                new GroupProject{ ProjectName = "PROJECT ONE", ProjectNamePoints ="100"},
                new GroupProject{ ProjectName = "PROJECT ONE", ProjectNamePoints ="100"},
                new GroupProject{ ProjectName = "PROJECT ONE", ProjectNamePoints ="100"},
                new GroupProject{ ProjectName = "PROJECT ONE", ProjectNamePoints ="100"}
            };
            DisplayAlert("Rewards won List", "Reward 1 - 100\nReward 2 - 100\nReward 3 - 100\nReward 4 - 100\nReward 1 - 100\nReward 1 - 100", "OK");
            
        }

        #region GroupTESTProject
        public class GroupProject
        {
            public string ProjectName { get; set; }
            public string ImageURLProject { get; set; }
            public string ProjectNamePoints { get; set; }

            public override string ToString()
            {
                return ProjectName;
            }
        }
#endregion
    }
}
